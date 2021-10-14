package board;

import inventory.Resources;
import org.junit.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WonderTest {

    @Test
    public void canPay() throws Exception {
        PlayerWithInventory playerWithInventory = new PlayerWithInventory(new Player(("Toto"), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        Wonder wonder = new Wonder("RHODOS_A", new Trade(Resources.ORE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.CLAY, 3)),
                        Arrays.asList(new Trade(Resources.ORE, 4))));

        assertFalse(wonder.canPay(playerWithInventory.getInventory()));  // False, parce que on a pas les ressources nécessaires

        playerWithInventory.getInventory().addQtyResource(Resources.WOOD, 2);
        playerWithInventory.getInventory().addQtyResource(Resources.CLAY, 3);
        playerWithInventory.getInventory().addQtyResource(Resources.ORE, 4);

        assertTrue(wonder.canPay(playerWithInventory.getInventory()));  // True, maintenant on les a

    }

    @Test
    public void pay() throws Exception {
        PlayerWithInventory playerWithInventory = new PlayerWithInventory(new Player(("Toto"), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        Wonder wonder = new Wonder("RHODOS_A", new Trade(Resources.ORE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.CLAY, 3)),
                        Arrays.asList(new Trade(Resources.ORE, 4))));


        playerWithInventory.getInventory().setWonder(wonder);

        assertEquals(wonder, playerWithInventory.getInventory().getWonder());  // Merveille liée et bien reconnue

        playerWithInventory.getInventory().addQtyResource(Resources.WOOD, 2);
        playerWithInventory.getInventory().addQtyResource(Resources.CLAY, 3);
        playerWithInventory.getInventory().addQtyResource(Resources.ORE, 4);

        assertEquals(2, playerWithInventory.getInventory().getQtyResource(Resources.WOOD));
        assertEquals(3, playerWithInventory.getInventory().getQtyResource(Resources.CLAY)); // Trio de Tests pour vérifier les résources nécessaires
        assertEquals(4 + 1, playerWithInventory.getInventory().getQtyResource(Resources.ORE)); // 4 ajoutées, 1 implémentée avec la merveille

        //De base, wonderLevel = 0
        wonder.pay(playerWithInventory);

        //assertEquals(0 , playerWithInventory.getInventory().getQtyResource(Resources.WOOD)); // Trio de Tests pour vérifier l'absence des résources


    }

    @Test
    public void giveReward() throws Exception {
        PlayerWithInventory playerWithInventory = new PlayerWithInventory(new Player(("Toto"), StrategyFactory.DIFFICULTY_LEVEL.EASY));
        Wonder wonder = new Wonder("RHODOS_A", new Trade(Resources.ORE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.CLAY, 3)),
                        Arrays.asList(new Trade(Resources.ORE, 4))));

        playerWithInventory.getInventory().setWonder(wonder);
        assertEquals(wonder, playerWithInventory.getInventory().getWonder());

        //wonderLevel = 0
        wonder.giveReward(playerWithInventory);
        assertEquals(3, playerWithInventory.getInventory().getQtyResource(Resources.VICTORY_POINT));  // Trio de tests pour vérifier la Reward


    }
}
