package inventory;

import board.Card;
import board.Trade;
import game.Age;
import org.junit.jupiter.api.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointsCalculatorTest {

    /*
    @Test
    public void calculMilitaire() throws Exception {
        PlayerWithInventory player1 = new PlayerWithInventory(new Player(("Player 1"), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        PlayerWithInventory player2 = new PlayerWithInventory(new Player(("Player 2"), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        PlayerWithInventory player3 = new PlayerWithInventory(new Player(("Player 3"), StrategyFactory.DIFFICULTY_LEVEL.EASY));

        LinkedList<PlayerWithInventory> playersWithInventories = new LinkedList<>();
        playersWithInventories.add(player1);
        playersWithInventories.add(player2);
        playersWithInventories.add(player3);

        new Card("Stockade", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), null, Card.Type.MILITARY, null, null).pay(player1);
        new Card("Stockade", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 5)), null, Card.Type.MILITARY, null, null).pay(player1);
        new Card("Stockade", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 5)), null, Card.Type.MILITARY, null, null).pay(player3);

        Hashtable<PlayerWithInventory, Integer> VP = new Hashtable<>();
        VP.put(player1, 2);
        VP.put(player2, -2);
        VP.put(player3, 0);

        assertEquals(VP, PointsCalculator.calculMilitaryPoints(playersWithInventories));

    }

     */


    @Test
    public void calculGoldPoints() throws Exception {
        PlayerWithInventory playerWithInventory = new PlayerWithInventory(new Player(("Player 1"), StrategyFactory.DIFFICULTY_LEVEL.EASY));

        assertEquals(1, PointsCalculator.calculGoldPoints(playerWithInventory.getInventory()));
        playerWithInventory.getInventory().addQtyResource(Resources.COINS, 3);
        assertEquals(2, PointsCalculator.calculGoldPoints(playerWithInventory.getInventory()));
    }

    @Test
    void calculCivilPoints() throws Exception {
        PlayerWithInventory player1 = new PlayerWithInventory(new Player(("Player 1"), StrategyFactory.DIFFICULTY_LEVEL.EASY));

        List<Card> cardsInHand = new ArrayList<>();
        cardsInHand.add(new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 10)), null, Card.Type.CIVIL, null, null));
        cardsInHand.add(new Card("Workshop", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Card.Type.CIVIL, null, null));
        player1.getInventory().setCardsInHand(cardsInHand);
        player1.getInventory().cardPlayed(new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 10)), null, Card.Type.CIVIL, null, null));

        assertEquals(10, PointsCalculator.calculCivilPoints(player1.getInventory()));

        new Card("Workshop", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Card.Type.CIVIL, null, null).pay(player1);

        assertEquals(11, PointsCalculator.calculCivilPoints(player1.getInventory()));
    }

    @Test
    void calculMilitaryPoints() throws Exception {
        PlayerWithInventory player1 = new PlayerWithInventory(new Player(("Player 1"), StrategyFactory.DIFFICULTY_LEVEL.EASY));

        List<Card> cardsInHand = new ArrayList<>();
        cardsInHand.add(new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 10)), null, Card.Type.MILITARY, null, null));
        cardsInHand.add(new Card("Workshop", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), null, Card.Type.MILITARY, null, null));
        player1.getInventory().setCardsInHand(cardsInHand);

        new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 10)), null, Card.Type.MILITARY, null, null).pay(player1);

        assertEquals(10, player1.getInventory().getMilitaryPointsOfPlayer());
    }

    @Test
    void calculSciencePoints() throws Exception {
        PlayerWithInventory player1 = new PlayerWithInventory(new Player(("Player 1"), StrategyFactory.DIFFICULTY_LEVEL.EASY));

        new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)), null, Card.Type.SCIENTIFIC, null, null).pay(player1);
        assertEquals(1, PointsCalculator.calculSciencePoints(player1.getInventory()));

        new Card("Workshop", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)), null, Card.Type.SCIENTIFIC, null, null).pay(player1);
        assertEquals(4, PointsCalculator.calculSciencePoints(player1.getInventory()));
    }
}
