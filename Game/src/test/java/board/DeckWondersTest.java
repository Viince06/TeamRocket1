package board;

import org.junit.jupiter.api.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class DeckWondersTest {

    @Test
    //Teste si la merveille prise est bien celle qui est donnée au joueur, pour toutes les joueurs de la partie
    public void wondersDistribution() throws Exception {
        DeckWonders deckWonders = new DeckWonders();
        deckWonders.initialize();

        LinkedList<PlayerWithInventory> playersWithInventories = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            playersWithInventories.add(new PlayerWithInventory(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY)));
        }

        Deque<Wonder> wonders = new ArrayDeque<>(deckWonders.getWonders());
        for (PlayerWithInventory playerWithInventory : playersWithInventories) {
            Wonder wonder = wonders.pop();
            playerWithInventory.getInventory().setWonder(wonder);
            assertEquals(wonder, playerWithInventory.getInventory().getWonder());

        }
    }
}

