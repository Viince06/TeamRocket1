package strategy;

import board.Card;
import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static inventory.Resources.MILITARY_POINT;

public class MilitaryStrategy extends PlayStrategy implements IStrategy {

    private IInventoryReadOnly inventory;
    private IInventoryReadOnly inventoryLeft;
    private IInventoryReadOnly inventoryRight;


    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) {
        this.inventoryRight = inventoryRight;
        this.inventoryLeft = inventoryLeft;
        this.inventory = inventory;

        super.setPriority(Collections.singletonList(Card.Type.MILITARY));

        List<PlayerAction> actions = super.playCard(inventory, inventoryLeft, inventoryRight);
        if (actions.size() == 1) {// une seule carte on a pas le choix on prend la seule dans la liste
            return actions.get(0);
        } else {
            return chooseActionFromList(actions);// une liste de plus d'un elem veut dire qu'on doit choisir dans la liste de cartes militaires
        }
    }


    @Override
    public Resources chooseReward(ArrayList<Trade> trades) {
        Trade max = trades.get(0);
        int qty = 0;
        //je met 0 comme default mais cela changera dans le if
        for (Trade trade : trades) {
            //si la resource est militaire et la quantite de la recompense est plus grand que ceux d'avant je le garde
            if ((trade.getResource() == MILITARY_POINT) && (trade.getQuantity() > qty)) {
                max = trade;
                qty = trade.getQuantity();
            }
        }
        return max.getResource();
    }


    /**
     * Cherche la carte militaire ayant la plus grosse valeur parmi celles qui sont jouable
     *
     * @param playableAction a été déterminé par chooseAction
     * @return Une playerAction pour que chooseAction renvoie l'action que la stratégie va employer
     */

    @Override
    public PlayerAction chooseActionFromList(List<PlayerAction> playableAction) {
        return super.chooseSameActionFromList(playableAction);
    }

    public static List<Card.Type> getCardType() {
        return Collections.singletonList(Card.Type.MILITARY);
    }

    @Override
    public String getStrategyName() {
        return "Military Strategy";
    }


    @Override
    public boolean maximumReached() {
        return super.checkMaximumReached(inventory, inventoryLeft, inventoryRight, Collections.singletonList(MILITARY_POINT));
    }
}