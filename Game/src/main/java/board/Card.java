package board;

import game.Age;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerWithInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Card implements ICard {
    private static final Logger log = LoggerFactory.getLogger(Card.class);

    private final String name;
    private final Age age;
    private final int nbPlayersMin;
    private final List<Trade> reward;
    private final List<Trade> cost;
    private final Type cardType;
    private Trade chosenReward;
    private final String previousCard;
    private final List<String> nextCard;

    public enum Type {
        RAW,            //marron
        HANDMADE,       //gris
        CIVIL,          //bleue
        SCIENTIFIC,     //vert
        TRADE,          //jaune
        MILITARY,       //rouge
        GUILD           //violet
    }

    public Card(String name, Age age, int nbPlayersMin, List<Trade> reward, List<Trade> cost, Type type, String previousCard, List<String> nextCard) {
        this.name = name;
        this.age = age;
        this.nbPlayersMin = nbPlayersMin;
        this.reward = reward == null ? new ArrayList<>() : reward;
        this.cost = cost == null ? new ArrayList<>() : cost;
        this.cardType = type;
        this.chosenReward = this.reward.get(0);
        this.previousCard = previousCard;
        this.nextCard = nextCard;
    }

    public String getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public int getNbPlayersMin() {
        return nbPlayersMin;
    }

    public Type getCardType() {
        return cardType;
    }

    public List<Trade> getReward() {
        return reward;
    }

    public List<Trade> getCost() {
        return cost;
    }

    public Trade getChosenReward() {
        return chosenReward;
    }

    public String getPreviousCard() {
        return previousCard;
    }

    public List<String> getNextCard() {
        return nextCard;
    }

    public boolean canPay(IInventoryReadOnly inventory) {
        return canPay(inventory, new ArrayList<>());
    }

    @Override
    public boolean canPay(IInventoryReadOnly inventory, List<Trade> resourcesFromExchanges) {
        if (resourcesFromExchanges == null) {
            resourcesFromExchanges = new ArrayList<>();
        }

        for (Trade trade : this.cost) {
            int totalQtyAvailable = inventory.getQtyResource(trade.getResource()) + resourcesFromExchanges.stream().filter(t -> trade.getResource().equals(t.getResource())).mapToInt(Trade::getQuantity).sum();
            if (totalQtyAvailable < trade.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    public void pay(PlayerWithInventory player) throws Exception {
        pay(player, new ArrayList<>());
    }

    @Override
    public void pay(PlayerWithInventory player, List<Trade> resourcesFromExchanges) throws Exception {

        // Est-ce que la sous évolution du batiment est présente dans l'inventaire ? Alors on paye
        if (this.previousCard == null || player.getInventory().getCardsPlayed().stream().filter(card -> card.name.equals(this.previousCard)).findFirst().isEmpty()) {
            if (!canPay(player.getInventory(), resourcesFromExchanges)) {
                log.error("Le joueur {} n'a pas les ressources nécessaires pour acheter la carte {}", player.getPlayer().getName(), this.name);
                throw new Exception(String.format("Le joueur %s n'a pas les ressources nécessaires pour acheter la carte '%s'", player.getPlayer().getName(), this.name));
            }

            for (Trade trade : this.cost) { //on n'enlève de l'inventaire que l'argent, le total des ressources ne doit pas diminuer
                if (trade.getResource() == Resources.COINS) {
                    player.getInventory().addQtyResource(trade.getResource(), -trade.getQuantity());
                }
            }

        }

        player.getInventory().cardPlayed(this);
        giveReward(player);
    }

    @Override
    public void giveReward(PlayerWithInventory player) throws Exception {
        if (this.reward.size() == 1) {
            player.getInventory().addQtyResource(this.reward.get(0).getResource(), this.reward.get(0).getQuantity());
            this.chosenReward = this.reward.get(0);
        } else if (this.reward.size() >= 1) {
            Resources chosenResource = player.getPlayer().getStrategy().chooseReward(new ArrayList<>(this.reward));
            Optional<Trade> chosenTrade = this.reward.stream().filter(trade -> trade.getResource() == chosenResource).findFirst();
            if (chosenTrade.isEmpty()) {
                log.error("La récompense choisie par le joueur ne fait pas parti des récompenses de la carte");
                throw new Exception("La récompense choisie par le joueur ne fait pas parti des récompenses de la carte");
            }
            player.getInventory().addQtyResource(chosenResource, chosenTrade.get().getQuantity());
            this.chosenReward = chosenTrade.get();
        }
    }

    //Should it be private?
    public boolean hasNextCard(PlayerWithInventory player) {
        Optional<Card> firstOption = player.getInventory().getCardsInHand().stream()
                .filter(card -> card.name.contains(this.nextCard.get(0))).findAny();

        //When there are 2 in a list?
        if (this.nextCard.size() == 2) {
            Optional<Card> secondOption = player.getInventory().getCardsInHand().stream()
                    .filter(card -> card.name.contains(this.nextCard.get(1))).findAny();

            return firstOption.isPresent() || secondOption.isPresent();

        } else {
            return firstOption.isPresent();
        }
    }
}

