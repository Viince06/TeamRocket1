package cucumbertests;

import io.cucumber.java8.En;
import player.Player;
import strategy.StrategyFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerStepDefs implements En {
    Player player;

    public PlayerStepDefs() {

        Given("a player with name {string}",
                (String playerName) ->
                {
                    player = new Player(playerName, StrategyFactory.DIFFICULTY_LEVEL.EASY);
                });

        When("{string} requests his name",
                (String nom) -> {

                    assertEquals(nom, player.getName());
                });
    }
}