
package board;

import org.junit.jupiter.api.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void CardDistribution() throws Exception {
        Deck deck = new Deck();
        deck.initialize();

        LinkedList<PlayerWithInventory> playersWithInventories = new LinkedList<>();


        for (int i = 0; i < 4; i++) {
            playersWithInventories.add(new PlayerWithInventory(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY)));
        }

        Deque<Card> cards = new ArrayDeque<>(deck.getCards());
        for (PlayerWithInventory playerWithInventory : playersWithInventories) {
            Card card = cards.pop();
            playerWithInventory.getInventory().getCardsInHand().add(card);
            assertEquals(card, playerWithInventory.getInventory().getCardsInHand().get(0));
        }

    }

}


