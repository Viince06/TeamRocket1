package strategy;

import board.Card;
import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResourceStrategy extends PlayStrategy implements IStrategy {

    private IInventoryReadOnly inventory;
    private IInventoryReadOnly inventoryLeft;
    private IInventoryReadOnly inventoryRight;

    private List<Resources> resourcesNeeded = Arrays.asList(Resources.WOOD, Resources.STONE, Resources.CLAY, Resources.ORE, Resources.GLASS, Resources.PAPYRUS, Resources.LOOM);

    /**
     * Appelle la fonction playCard de PlayStrategy pour déterminer la carte à jouer selon la priorité
     *
     * @param inventory      L'inventaire du joueur
     * @param inventoryLeft  L'inventaire du joueur de gauche
     * @param inventoryRight L'inventaire du joueur de droite
     * @return Une player Action qui contient l'action à jouer
     */
    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) {
        this.inventoryRight = inventoryRight;
        this.inventoryLeft = inventoryLeft;
        this.inventory = inventory;
        super.setPriority(Arrays.asList(Card.Type.HANDMADE, Card.Type.RAW));

        List<PlayerAction> actions = super.playCard(inventory, inventoryLeft, inventoryRight);

        if (actions.size() == 1) {// une seule carte on a pas le choix on prend la seule dans la liste
            return actions.get(0);
        } else {
            return chooseActionFromList(actions);// une liste de plus d'un elem veut dire qu'on doit choisir dans la liste de cartes de ressources
        }
    }

    /**
     * Choisit la récompense sur une carte, prend en priorité la ressource qu'on a le moins dans l'inventaire
     *
     * @param trades La liste des ressources dans lesquelles on va choisir
     * @return La ressource choisie
     */
    @Override
    public Resources chooseReward(ArrayList<Trade> trades) {
        Trade choice = trades.get(0); //la 1ere ressource est prise par défaut si par la suite on ne trouve pas mieux
        int qty = 0;
        List<Resources> resourcesOwn = new ArrayList<>(); //on fait une liste des cartes qui génèrent des ressources, que l'on a déjà
        for (Card card : this.inventory.getCardsPlayed()) {
            resourcesOwn.add(card.getChosenReward().getResource());
        }
        for (Trade trade : trades) {
            if (!resourcesOwn.contains(trade.getResource()) && (trade.getQuantity() > qty)) {
                choice = trade;
                qty = trade.getQuantity();
            }
        }
        return choice.getResource();
    }

    /**
     * Cherche dans les playerAction la carte qui permettra de compléter les ressources de l'inventaire.
     * On cherche dans l'inventaire la ressource dont la quantité est la plus petite et on va essayer
     * de jouer en priorité une carte dans les playableAction qui contient aussi cette ressource.
     * Si ce n'est pas possible, on prendra la 2nd ressource qui est la moins présente dans l'inventaire, puis les
     * autres.
     *
     * @param playableAction La liste des actions qui peuvent être jouées et qui sont de type ressource marron ou grise
     * @return une PlayerAction qui sera jouée par le joueur
     */

    @Override
    public PlayerAction chooseActionFromList(List<PlayerAction> playableAction) {
        return chooseActionList(playableAction, resourcesNeeded, inventory);
    }

    public static List<Card.Type> getCardType() {
        return Arrays.asList(Card.Type.RAW, Card.Type.HANDMADE);
    }

    @Override
    public String getStrategyName() {
        return "Resource Strategy";
    }

    @Override
    public boolean maximumReached() {
        return super.checkMaximumReached(inventory, inventoryLeft, inventoryRight, resourcesNeeded);
    }
}

