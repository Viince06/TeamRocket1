package board;

import inventory.IInventoryReadOnly;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerWithInventory;

import java.util.ArrayList;
import java.util.List;

public class Wonder implements ICard {

    private static final Logger log = LoggerFactory.getLogger(Wonder.class);

    private final String name;
    private final List<List<Trade>> reward;
    private final List<List<Trade>> cost;
    private final Trade ressourceWonder;
    private int wonderLevel;


    /**
     * ressourceWonder est la ressource gratuite donnée par la merveille
     * La liste des reward contient des listes de Trade pour les récompenses de chaque étape de la merveille
     * La liste des coûts contient contient des listes de Trade pour les coûts de chaque étape de la merveille
     * reward[0] = étape 1
     * cost[0] = coût étape 1
     * Une merveille peut avoir de 2 à 4 étapes, alors les listes auront jusqu'à 4 sous-listes
     */

    public Wonder(String name, Trade ressourceWonder, List<List<Trade>> reward, List<List<Trade>> cost) {
        this.name = name;
        this.ressourceWonder = ressourceWonder;
        this.reward = reward;
        this.cost = cost;
        wonderLevel = 0;
    }

    public String getName() {
        return name;
    }

    public List<List<Trade>> getReward() {
        return reward;
    }

    public List<List<Trade>> getCost() {
        return cost;
    }

    public Trade getRessourceWonder() {
        return ressourceWonder;
    }

    public int getWonderLevel() {
        return wonderLevel;
    }

    /**
     * Détermine si un joueur a les ressources nécessaire pour payer l'étape de merveille suivante,
     *
     * @param inventory l'inventaire du joueur
     * @return true or false
     */
    public boolean canPay(IInventoryReadOnly inventory) {
        return canPay(inventory, new ArrayList<>());
    }

    @Override
    public boolean canPay(IInventoryReadOnly inventory, List<Trade> resourcesFromExchanges) {
        if (resourcesFromExchanges == null) {
            resourcesFromExchanges = new ArrayList<>();
        }
        if (this.wonderLevel < this.cost.size()) {
            List<Trade> tradeWonder = this.cost.get(this.wonderLevel); //récupère la liste des couts pour le niveau de merveille suivant
            for (Trade trade : tradeWonder) {
                int totalQtyAvailable = inventory.getQtyResource(trade.getResource()) + resourcesFromExchanges.stream().filter(t -> trade.getResource().equals(t.getResource())).mapToInt(Trade::getQuantity).sum();
                if (totalQtyAvailable < trade.getQuantity()) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    /**
     * Si le joueur a le nombre de ressources et d'argent nécessaire pour acheter une étape de merveille,
     * décompte l'argent nécessaire à payer la merveille, (et pas les ressources car elles sont permanente)
     *
     * @param player le joueur qui veut acheter l'étape
     * @throws Exception
     */
    public void pay(PlayerWithInventory player) throws Exception {
        pay(player, new ArrayList<>());
    }

    @Override
    public void pay(PlayerWithInventory player, List<Trade> resourcesFromExchanges) throws Exception {
        if (!canPay(player.getInventory(), resourcesFromExchanges)) {
            log.error("Le joueur {} n'a pas les ressources nécessaires pour acheter l'étape de merveille {}", player.getPlayer().getName(), this.name);
            throw new Exception(String.format("Le joueur %s n'a pas les ressources nécessaires pour acheter l'étape de merveille '%s'", player.getPlayer().getName(), this.name));
        }

        List<Trade> tradeWonder = this.cost.get(this.wonderLevel);

        for (Trade trade : tradeWonder) {
            if (trade.getResource() == Resources.COINS) {
                player.getInventory().addQtyResource(trade.getResource(), -trade.getQuantity());
            }
            log.debug("le joueur a payé {} de {}", trade.getQuantity(), trade.getResource().name());
        }
        giveReward(player);
    }

    /**
     * Ajoute la ou les récompenses à l'inventaire du joueur, il n'y a pas besoin de choisir parmi les récompenses.
     */
    @Override
    public void giveReward(PlayerWithInventory player) {
        List<Trade> rewardWonder = this.reward.get(this.wonderLevel);

        for (Trade trade : rewardWonder) {
            player.getInventory().addQtyResource(trade.getResource(), trade.getQuantity());
        }
        this.wonderLevel++;
    }
}
