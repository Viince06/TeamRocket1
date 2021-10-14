package strategy;

import board.Card;
import board.Trade;
import game.Exchange;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;

public class PlayStrategy {

    private List<Card.Type> priority;
    private static final int maxLevel = 3;

    protected void setPriority(List<Card.Type> priority) {
        this.priority = priority;
    }

    protected List<Card.Type> getPriority() {
        return this.priority;
    }

    /**
     * Fonction générale qui cherche une carte du type de la priorité, ou bien si elle n'en trouve pas la 1ere carte jouable qui soit
     * gratuite/pour payer la merveille/une évolution d'une carte posée
     *
     * @param inventory      l'inventaire du joueur
     * @param inventoryLeft  l'inventaire de son voisin de gauche
     * @param inventoryRight l'inventaire de son voisin de droite
     * @return Une liste d'une ou plusieurs PlayerAction
     */
    public List<PlayerAction> playCard(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) {

        List<PlayerAction> playableAction = new ArrayList<>();
        List<PlayerAction> returnPlayableAction = new ArrayList<>();

        List<Card> noCostCards = new ArrayList<>();//contiendra toutes les cartes gratuites que je peux jouer
        List<Card> evolutionCards = new ArrayList<>();//contiendra toutes les sous evolutions que je peux jouer
        List<Card> wonderCards = new ArrayList<>();//contiendra toutes les cartes merveilles que je peux jouer
        List<Card> cardsInHand = inventory.getCardsInHand();

        for (Card.Type type : priority) {
            for (Card currentCard : cardsInHand) {
                List<Trade> cardCosts = currentCard.getCost();

                //si la carte est du type que je veux, alors je le rajoute dans la liste
                if (currentCard.getCardType() == type) {

                    List<Exchange> exchangeList = isPlayable(cardCosts, inventory, inventoryLeft, inventoryRight);
                    if (exchangeList == null) {
                        continue;
                    } else if (exchangeList.isEmpty()) {
                        playableAction.add(new PlayerAction(currentCard, PlayerAction.Choice.PLAY));
                    } else {
                        playableAction.add(new PlayerAction(currentCard, exchangeList));
                    }
                }

                //la carte est gratuite je le rajoute dans la liste
                if (cardCosts.isEmpty()) {
                    noCostCards.add(currentCard);
                }

                //la carte est la sous evolution je le rajoute dans la liste
                if (inventory.getCardsPlayed().stream().map(Card::getName).anyMatch(s -> s.equals(currentCard.getPreviousCard()))) {
                    evolutionCards.add(currentCard);
                }

                //la carte est pour ma merveille je le rajoute dans la liste
                if (inventory.getWonder().canPay(inventory)) {
                    wonderCards.add(currentCard);
                }
            }
        }

        if (evolutionCards.isEmpty()) {//si on a pas des cartes sous evolutions
            if (wonderCards.isEmpty()) {//si on a pas des cartes pour ma merveille
                if (playableAction.isEmpty()) {//si on a pas de carte du TYPE demandee
                    if (noCostCards.isEmpty()) {//si on a pas des cartes gratuites
                        returnPlayableAction.add(sacrificeCard(cardsInHand.get(0)));//je sacrifie ma premiere carte
                    } else {
                        returnPlayableAction.add(new PlayerAction(noCostCards.get(0), PlayerAction.Choice.PLAY));
                    }
                } else {
                    returnPlayableAction.addAll(playableAction);
                }
            } else {
                returnPlayableAction.add(new PlayerAction(wonderCards.get(0), PlayerAction.Choice.WONDER));
            }
        } else {
            returnPlayableAction.add(new PlayerAction(evolutionCards.get(0), PlayerAction.Choice.PLAY));
        }
        /*
         * Je renvoie la liste returnPlayableAction qui contiendra les cartes que je peux jouer dans l'ordre suivante:
         *  1) Les cartes de sous evolutions
         *  2) Les cartes pour ma merveille
         *  3) Les cartes du type demandee par la strategie choisi
         *  4) Les cartes gratuites
         */
        return returnPlayableAction;
    }


    /**
     * Vérifie si une carte est jouable et calcule les échanges nécessaires si besoin
     *
     * @return - null si la carte n'est pas jouable
     * - EmptyList si aucun échange n'est requis
     * - List<Exchange> nécessaire à l'achat de la carte
     */

    protected List<Exchange> isPlayable(List<Trade> cardCost, IInventoryReadOnly inventory, IInventoryReadOnly left, IInventoryReadOnly right) {
        int moneyAvailable = inventory.getQtyResource(Resources.COINS);
        List<Exchange> exchangeNeeded = new ArrayList<>();
        for (Trade currentCost : cardCost) {
            Resources resources = currentCost.getResource();

            // Si la carte s'achete avec de l'or et qu'on en a pas assez
            if (Resources.COINS.equals(resources) && currentCost.getQuantity() > moneyAvailable) {
                return null;
            }

            int qtyNeeded = currentCost.getQuantity() - inventory.getQtyResource(resources);
            // Si la carte s'achete avec une quantité de ressources qu'on possède déjà
            if (qtyNeeded <= 0) {
                continue;
            }

            // Si on a pas suffisamment d'argent pour faire un échange
            if (qtyNeeded * 2 > moneyAvailable) {
                return null;
            }

            int leftResourceQty = left.getQtyResource(resources);
            int rightResourceQty = right.getQtyResource(resources);

            // Si mes voisins n'ont pas les ressources nécessaires
            if (leftResourceQty + rightResourceQty < qtyNeeded) {
                return null;
            }

            int qtyInExchange = Math.min(leftResourceQty, qtyNeeded);
            if (qtyInExchange > 0) {
                exchangeNeeded.add(new Exchange(true, new Trade(resources, qtyInExchange)));
                qtyNeeded -= qtyInExchange;
                moneyAvailable -= qtyInExchange * 2;
            }
            qtyInExchange = Math.min(rightResourceQty, qtyNeeded);
            if (qtyInExchange > 0) {
                exchangeNeeded.add(new Exchange(false, new Trade(resources, qtyInExchange)));
                moneyAvailable -= qtyInExchange * 2;
            }
        }
        return exchangeNeeded;
    }

    protected PlayerAction sacrificeCard(Card cardToSacrifice) {
        return new PlayerAction(cardToSacrifice, PlayerAction.Choice.SACRIFICE);
    }

    protected PlayerAction chooseSameActionFromList(List<PlayerAction> playableAction) {
        PlayerAction choice = playableAction.get(0);
        int max = playableAction.get(0).getCard().getReward().get(0).getQuantity();
        for (PlayerAction playerAction : playableAction) {
            List<Trade> rewards = playerAction.getCard().getReward();
            int val = rewards.get(0).getQuantity();
            if (val > max) {
                choice = playerAction;
                max = val;
            }
        }
        return choice;
    }

    protected PlayerAction chooseActionList(List<PlayerAction> playableAction, List<Resources> resourcesNeeded, IInventoryReadOnly inventory) {
        PlayerAction choice = playableAction.get(0);
        List<Trade> myResources = new ArrayList<>();
        //Je setup une Enum qui me servira à stocker la playerAction qui sera utilisée pour la ressource correspondante
        EnumMap<Resources, PlayerAction> qtyInPlayerAction = new EnumMap<>(Resources.class);
        for (Resources resource : resourcesNeeded) {
            myResources.add(new Trade(resource, inventory.getQtyResource(resource)));
            qtyInPlayerAction.put(resource, null);
        }
        //Je trie une liste composée d'objets Trade<Ressource,quantité> en sens inverse
        if (resourcesNeeded.contains(Resources.WOOD)) {
            myResources.sort(Comparator.comparingInt(Trade::getQuantity).reversed());
        } else {
            myResources.sort(Comparator.comparingInt(Trade::getQuantity));
        }

        //J'associe chaque type de symbole avec une playerAction (dès que j'en ai une je l'associe, dans les fait on jouera donc la dernière carte qui a le symbole correspondant)
        for (PlayerAction playerAction : playableAction) {
            Resources resources = playerAction.getCard().getReward().get(0).getResource();
            for (Resources resource : resourcesNeeded) {
                if (resources == resource) {
                    qtyInPlayerAction.put(resource, playerAction);
                }
            }
        }

        //Je regarde si le symbole min dans mon inventaire a une playerAction liée dans l'enum qui les stocke
        for (Trade trade : myResources) {
            PlayerAction playerAction = qtyInPlayerAction.get(trade.getResource());
            if (playerAction != null) {
                return playerAction;
            }
        }
        return choice;
    }

    /**
     * Regarde si on est arrivé aux limites des stratégies. Utilisé par la stratégie Militaire et la stratégie Ressources.
     *
     * @param inventory       L'inventaire du joueur
     * @param inventoryLeft   L'inventaire du joueur de gauche
     * @param inventoryRight  L'inventaire du joueur de droite
     * @param resourcesNeeded Les ressources dont
     * @return True si on a trop de ressources (Points militaire si stratégie militaire, types de ressources différents pour la stratégie Ressource)
     */
    protected boolean checkMaximumReached(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight, List<Resources> resourcesNeeded) {
        Resources.ResourceType type = resourcesNeeded.get(0).getType();

        // Si on est dans la stratégie Militaire (Seulement la stratégie militaire utilisera le type Others)
        if (type == Resources.ResourceType.OTHERS) {
            int playerPoints = 0;
            int playerLeftPoints = 0;
            int playerRightPoints = 0;

            for (Resources re : resourcesNeeded) {
                playerPoints += inventory.getQtyResource(re);
                playerLeftPoints += inventoryLeft.getQtyResource(re);
                playerRightPoints += inventoryRight.getQtyResource(re);
            }
            int maxOpponent = Math.max(playerLeftPoints, playerRightPoints);
            return (playerPoints - maxOpponent) >= maxLevel;

        }

        //Si on est dans la stratégie Ressource
        if (type == Resources.ResourceType.RAW_MATERIALS || type == Resources.ResourceType.MANUFACTURED_GOODS) {
            int totMax = 0;

            //on regarde si on a plus de 5 type de ressources différentes
            for (Resources ress : resourcesNeeded) {
                int r = inventory.getQtyResource(ress);
                if (r > 0) {
                    totMax += 1;
                }
            }
            //si c'est le cas on renvoie true -> on arrêtera de faire la stratégie ressources
            return totMax >= 5;

        }
        return false;
    }
}
