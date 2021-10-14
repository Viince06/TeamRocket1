package strategy;

import board.Card;
import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomStrategy extends PlayStrategy implements IStrategy {
    private Random random = new Random();


    /**
     * Appelé par la classe Game, renvoit une carte à poser ou à défausser
     * et il choisi une carte en Random
     */
    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) {
        List<Card> cardsInHand = inventory.getCardsInHand();

        for (int i = 0; i < cardsInHand.size(); i++) {
            int cardNumber = random.nextInt(cardsInHand.size() - 1);
            List<Trade> cardCosts = cardsInHand.get(cardNumber).getCost();
            if (cardCosts.isEmpty()) {
                return new PlayerAction(cardsInHand.get(cardNumber), PlayerAction.Choice.PLAY);
            } else if (inventory.getWonder().canPay(inventory)) {
                return new PlayerAction(cardsInHand.get(i), PlayerAction.Choice.WONDER);
            }
        }
        return super.sacrificeCard(inventory.getCardsInHand().get(0));
    }

    @Override
    public Resources chooseReward(ArrayList<Trade> trades) {
        return trades.get(random.nextInt(trades.size() - 1)).getResource();
    }

    @Override
    public PlayerAction chooseActionFromList(List<PlayerAction> playableAction) {
        return playableAction.get(random.nextInt(playableAction.size() - 1));
    }

    @Override
    public String getStrategyName() {
        return "Random Strategy";
    }

    @Override
    public boolean maximumReached() {
        return false;
    }
}
