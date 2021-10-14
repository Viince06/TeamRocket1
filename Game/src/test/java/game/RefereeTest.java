package game;

import board.Card;
import board.Trade;
import inventory.Inventory;
import inventory.Resources;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import player.Player;
import player.PlayerAction;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RefereeTest {

    private Referee referee = new Referee();
    private Inventory emptyInventory = new Inventory();

    @Test
    public void isChoicePlayableTest1() throws Exception {
        PlayerWithInventory player = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
        PlayerAction.Choice choice = PlayerAction.Choice.PLAY;
        Card card = new Card("TestCard", Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), null, Card.Type.RAW, null, null);
        player.getInventory().setCardsInHand(Collections.singletonList(card));
        PlayerAction playerAction = new PlayerAction(card, choice);
        Assert.assertTrue(referee.isChoicePlayable(playerAction, player.getInventory(), emptyInventory, emptyInventory));

    }

    @Test
    public void isChoicePlayableTest2() throws Exception {
        PlayerWithInventory player = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
        PlayerAction.Choice choice = PlayerAction.Choice.SACRIFICE;
        Card card = new Card("TestCard", Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), null, Card.Type.RAW, null, null);
        player.getInventory().setCardsInHand(Collections.singletonList(card));
        PlayerAction playerAction = new PlayerAction(card, choice);
        Assert.assertTrue(referee.isChoicePlayable(playerAction, player.getInventory(), emptyInventory, emptyInventory));
    }


    @Test
    public void isChoicePlayableTest3() throws Exception {
        PlayerWithInventory player = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
        List<Trade> cost = new ArrayList<>();
        cost.add(new Trade(Resources.WOOD, 1));

        Card card = new Card("TestCard", Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), cost, Card.Type.RAW, null, null);
        List<Exchange> listOfExchange = new ArrayList<>();
        player.getInventory().setCardsInHand(Collections.singletonList(card));
        PlayerAction playerAction = new PlayerAction(card, listOfExchange);
        assertFalse(referee.isChoicePlayable(playerAction, player.getInventory(), emptyInventory, emptyInventory));

    }

    @Test
    public void isChoicePlayableTest4() throws Exception {
        PlayerWithInventory player = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
        List<Trade> cost = new ArrayList<>();
        cost.add(new Trade(Resources.WOOD, 1));

        Card card = new Card("TestCard", Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), cost, Card.Type.RAW, null, null);
        List<Exchange> listOfExchange = new ArrayList<>();
        listOfExchange.add(new Exchange(true, new Trade(Resources.WOOD, 1)));
        player.getInventory().setCardsInHand(Collections.singletonList(card));
        PlayerAction playerAction = new PlayerAction(card, listOfExchange);
        emptyInventory.addQtyResource(Resources.WOOD, 1);
        assertTrue(referee.isChoicePlayable(playerAction, player.getInventory(), emptyInventory, emptyInventory));
    }


    @Test
    public void getWinnerTest() throws Exception {

        List<Player> list = new ArrayList<Player>();
        for (int i = 0; i < 4; i++) {
            list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        }
        Game game = new Game(list);
        List<PlayerWithInventory> listOfPlayers = game.getPlayersWithInventories();
        game.getPlayersWithInventories().get(2).getInventory().addQtyResource(Resources.COINS, 1000);

        assertEquals(listOfPlayers.get(2), referee.getWinner(listOfPlayers).get(0));
    }


    @Test
    public void checkWinnerOfTwoTest() throws Exception {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        }
        Game game = new Game(list);

        List<PlayerWithInventory> listOfPlayers = game.getPlayersWithInventories();
        listOfPlayers.get(0).getInventory().addQtyResource(Resources.MILITARY_POINT, 100);

        assertEquals(listOfPlayers.get(0), Referee.checkWinnerOfTwo(listOfPlayers.get(0), listOfPlayers.get(1)));
    }


    @Test
    public void checkLooserOfTwoTest() throws Exception {
        List<Player> list = new ArrayList<Player>();
        for (int i = 0; i < 2; i++) {
            list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        }
        Game game = new Game(list);

        List<PlayerWithInventory> listOfPlayers = game.getPlayersWithInventories();
        listOfPlayers.get(0).getInventory().addQtyResource(Resources.MILITARY_POINT, 100);

        assertEquals(listOfPlayers.get(1), Referee.checkLooserOfTwo(listOfPlayers.get(0), listOfPlayers.get(1)));
    }
}