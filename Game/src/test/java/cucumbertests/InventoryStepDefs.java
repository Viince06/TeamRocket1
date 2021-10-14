package cucumbertests;

import inventory.Inventory;
import inventory.PointsCalculator;
import inventory.Resources;
import io.cucumber.java8.En;
import player.Player;
import strategy.StrategyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryStepDefs implements En {
    Inventory inventory;

    public InventoryStepDefs() {


        Given("a player with name {string} joins the game",
                (String playerName) ->
                {
                    new Player(playerName, StrategyFactory.DIFFICULTY_LEVEL.EASY);
                    inventory = new Inventory();
                });

        When("{string} requests his number of gold he gets {int}",
                (String nom, Integer numberOfGold) -> {
                    assertEquals(numberOfGold.intValue(), inventory.getQtyResource(Resources.COINS));
                });

        When("{string} gets {int} victory points from his gold",
                (String nom, Integer numberOfVictoryPoints) -> {
                    assertEquals(numberOfVictoryPoints.intValue(), PointsCalculator.calculGoldPoints(inventory));
                });
    }
}
