package cucumbertests;

import board.Card;
import board.Trade;
import game.Age;
import game.Exchange;
import game.Game;
import game.Referee;
import inventory.Inventory;
import inventory.Resources;
import io.cucumber.java8.En;
import player.Player;
import player.PlayerAction;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefereeStepDefs implements En {
    Referee referee = new Referee();
    Inventory playerInventory;
    PlayerAction playerAction;
    PlayerAction.Choice choice;
    Card card;
    Game game;
    List<PlayerWithInventory> listOfPlayers;
    Inventory emptyInventory = new Inventory();


    public RefereeStepDefs() {
        Given("a player with his inventory and name {string}",
                (String playerName) ->
                {
                    PlayerWithInventory player = new PlayerWithInventory(new Player(playerName, StrategyFactory.DIFFICULTY_LEVEL.EASY));
                    playerInventory = player.getInventory();
                });


        When("The player plays a card {string} with cost set to null",
                (String nameOfCard) -> {
                    choice = PlayerAction.Choice.PLAY;
                    card = new Card(nameOfCard, Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), null, Card.Type.RAW, null, null);
                    playerInventory.setCardsInHand(Collections.singletonList(card));
                    playerAction = new PlayerAction(card, choice);
                });

        Then("The move is a valid one as cost is {string}", (String cost) -> {
            assertTrue(referee.isChoicePlayable(playerAction, playerInventory, emptyInventory, emptyInventory));
        });


        When("The player sacrifices a card {string}",
                (String nameOfCard) -> {
                    choice = PlayerAction.Choice.SACRIFICE;
                    card = new Card(nameOfCard, Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), null, Card.Type.RAW, null, null);
                    playerInventory.setCardsInHand(Collections.singletonList(card));
                    playerAction = new PlayerAction(card, choice);
                });

        Then("The move is a valid and cost is {string}", (String cost) -> {
            assertTrue(referee.isChoicePlayable(playerAction, playerInventory, emptyInventory, emptyInventory));
        });


        When("The player play a card {string} with resource Wood of quantity 1 is needed and he has no Wood resource",
                (String nameOfCard) -> {
                    choice = PlayerAction.Choice.PLAY;
                    List<Trade> cost = new ArrayList<>();
                    cost.add(new Trade(Resources.WOOD, 1));

                    card = new Card(nameOfCard, Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), cost, Card.Type.RAW, null, null);
                    playerInventory.setCardsInHand(Collections.singletonList(card));
                    playerAction = new PlayerAction(card, choice);
                });

        Then("The move is invalid as cost is {int}", (Integer cost) -> {
            assertFalse(referee.isChoicePlayable(playerAction, playerInventory, emptyInventory, emptyInventory));
        });


        When("The player play a card {string} with Wood 1 is needed but he has no Wood resource",
                (String nameOfCard) -> {
                    choice = PlayerAction.Choice.PLAY;
                    List<Trade> cost = new ArrayList<>();
                    cost.add(new Trade(Resources.WOOD, 1));
                    card = new Card(nameOfCard, Age.AGE_1, 1, Arrays.asList(new Trade(Resources.WOOD, 10)), cost, Card.Type.RAW, null, null);
                    List<Exchange> listOfExchange = new ArrayList<>();
                    listOfExchange.add(new Exchange(true, new Trade(Resources.WOOD, 1)));
                    emptyInventory.addQtyResource(Resources.WOOD, 1);
                    playerInventory.setCardsInHand(Collections.singletonList(card));
                    playerAction = new PlayerAction(card, listOfExchange);
                });

        Then("He proposes a Trade with the player on the left for 1 Wood resource, the move is then valid as he has {int} Wood", (Integer traded) -> {
            assertTrue(referee.isChoicePlayable(playerAction, playerInventory, emptyInventory, emptyInventory));
        });


        When("the game is finished, Player 3 is the winner as he has been given {int} coins",
                (Integer coinsGiven) -> {
                    List<Player> list = new ArrayList<Player>();
                    for (int i = 0; i < 4; i++) {
                        list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
                    }
                    game = new Game(list);
                    listOfPlayers = game.getPlayersWithInventories();
                    game.getPlayersWithInventories().get(2).getInventory().addQtyResource(Resources.COINS, coinsGiven.intValue());
                });

        Then("in this case Player {int} is the winner", (Integer playerNumber) -> {
            assertEquals(game.getPlayersWithInventories().get(2), referee.getWinner(listOfPlayers).get(0));
        });


        When("the game is finished, the winner is checked by comparing their {string}",
                (String resource) -> {

                    List<Player> list = new ArrayList<Player>();
                    for (int i = 0; i < 2; i++) {
                        list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
                    }
                    game = new Game(list);

                    listOfPlayers = game.getPlayersWithInventories();
                    listOfPlayers.get(0).getInventory().addQtyResource(Resources.MILITARY_POINT, 100);

                });

        Then("in this case Player 2 is the winner as he has been given {int} Military Resources", (Integer militaryPonts) -> {
            assertEquals(listOfPlayers.get(0), Referee.checkWinnerOfTwo(listOfPlayers.get(0), listOfPlayers.get(1)));
        });


        When("the game is finished, the loser is the one with the least {string}",
                (String resource) -> {
                    List<Player> list = new ArrayList<Player>();
                    for (int i = 0; i < 2; i++) {
                        list.add(new Player("Player " + (i + 1), StrategyFactory.DIFFICULTY_LEVEL.EASY));
                    }
                    game = new Game(list);

                    listOfPlayers = game.getPlayersWithInventories();
                    listOfPlayers.get(0).getInventory().addQtyResource(Resources.MILITARY_POINT, 100);

                });

        Then("in this case Player 1 is the loser as he has {int} Military Resources", (Integer militaryPonts) -> {
            assertEquals(listOfPlayers.get(1), Referee.checkLooserOfTwo(listOfPlayers.get(0), listOfPlayers.get(1)));
        });

    }
}