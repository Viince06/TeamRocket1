package game;

import board.Deck;
import org.junit.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class GameTest {

    @Test
    public void GameTest() throws Exception {
        //Test Existance Referee
        Referee referee = new Referee();
        assertFalse(referee.toString().isBlank());

        //Test Existance Deck
        Deck deck = new Deck();
        deck.initialize();
        assertFalse(deck.toString().isEmpty());

        //Test Existance Joueurs
        LinkedList<PlayerWithInventory> playersWithInventories = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            playersWithInventories.add(new PlayerWithInventory(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY)));
            assertEquals(i + 1, playersWithInventories.size());
        }
    }
}