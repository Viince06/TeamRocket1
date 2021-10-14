package strategy;

import board.Card;
import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static board.Card.Type.CIVIL;

public class CivilStrategy extends PlayStrategy implements IStrategy {

    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) {
        super.setPriority(Collections.singletonList(CIVIL));

        List<PlayerAction> actions = super.playCard(inventory, inventoryLeft, inventoryRight);

        if (actions.size() == 1) {
            return actions.get(0);  // On prend la seule dans la liste
        } else {
            return chooseActionFromList(actions); // On va choisir selon la liste
        }
    }

    @Override
    public Resources chooseReward(ArrayList<Trade> trades) {
        return trades.get(0).getResource();
    }

    @Override
    public PlayerAction chooseActionFromList(List<PlayerAction> playableAction) {
        return super.chooseSameActionFromList(playableAction);
    }

    public static List<Card.Type> getCardType() {
        return Collections.singletonList(CIVIL);
    }

    @Override
    public String getStrategyName() {
        return "Civil Strategy";
    }

    @Override
    public boolean maximumReached() {
        return false;
    }

}
