package strategy;

import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.List;

public interface IStrategy {
    PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) throws Exception;

    Resources chooseReward(ArrayList<Trade> trades);

    PlayerAction chooseActionFromList(List<PlayerAction> playableAction);

    String getStrategyName();

    boolean maximumReached();
}