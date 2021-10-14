package player;

import board.Card;
import board.Trade;
import board.Wonder;
import game.Age;
import inventory.Inventory;
import inventory.Resources;
import org.junit.jupiter.api.Test;
import strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerTest {

    @Test
    public void chooseRewardTest() throws Exception {
        PlayerWithInventory player = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY)); //3 de coins, 0 du reste
        Trade trade1 = new Trade(Resources.WOOD, 1); //on sait que le joueur va prendre ça ...
        Trade trade2 = new Trade(Resources.GLASS, 1);
        ArrayList<Trade> trades = new ArrayList<>();
        trades.add(trade1);
        trades.add(trade2);
        Resources choix = player.getPlayer().getStrategy().chooseReward(trades); //...car le chooseReward prend le premier élément du trade
        assertEquals(Resources.WOOD, choix);
    }

    @Test
    public void chooseActionTest() throws Exception {

        PlayerWithInventory playerWithInventory = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
        Inventory inventory2 = new Inventory();
        Inventory inventory3 = new Inventory();

        Card firstCard = new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), Arrays.asList(new Trade(Resources.STONE, 1)), Card.Type.CIVIL, null, null);
        Card secondCard = new Card("Aqueduct", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)), Arrays.asList(new Trade(Resources.CLAY, 3)), Card.Type.CIVIL, "Baths", null);
        Card ress2 = new Card("Stone Pit", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.STONE, 1)), null, Card.Type.RAW, null, null);
        Wonder myWonder = new Wonder("myWonder", new Trade(Resources.ORE, 1), new ArrayList<>(), new ArrayList<>(Collections.singletonList(Collections.singletonList(new Trade(Resources.WOOD, 99)))));
        playerWithInventory.getInventory().setWonder(myWonder);

        List<Card> cardsInHand = new ArrayList<>();
        cardsInHand.add(secondCard);
        cardsInHand.add(firstCard);

        playerWithInventory.getInventory().setCardsInHand(cardsInHand);

        //Le bot va essayer 1) regarder si la carte est gratuite
        //2)essayer de l'acheter si elle est payante -> il n'a pas de ressources alors il ne pourra pas
        //3)recommence avec l'autre carte -> toujours pareil
        //4)alors il va sacrifier cette dernière carte
        assertEquals(PlayerAction.Choice.SACRIFICE, playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getChoice());
        //PlayerAction test = new PlayerAction(playerWithInventory.getInventory().getCardsInHand().get(1), PlayerAction.Choice.SACRIFICE);
        //PlayerAction actual = playerWithInventory.getPlayer().chooseAction(playerWithInventory.getInventory(), inventory2,inventory3);


        //TEST 2 : le player a une carte gratuite en main, il va la poser (on doit voir lumber yard posé)
        cardsInHand.add(ress2);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(PlayerAction.Choice.PLAY, playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getChoice());
        //le choix doit être égal à PLAY
        assertEquals(ress2.getName(), playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getCard().getName());
        //le nom de la carte doit être le nom de la carte "ress2"
        playerWithInventory.getInventory().cardPlayed(ress2); // on pose la carte pour qu'elle ne soit pas rejouée au prochaine tour
        cardsInHand.remove(ress2);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        playerWithInventory.getInventory().addQtyResource(Resources.STONE, 1);

        //TEST 3 : le player utilise la ressource qu'il vient de poser pour acheter "Baths" avec de la STONE
        assertEquals(PlayerAction.Choice.PLAY, playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getChoice());
        assertEquals(firstCard.getName(), playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getCard().getName());

        //TEST 4: Le joueur à gauche a la ressource pour que le joueur puisse acheter Aqueduct (3 CLAY)
        playerWithInventory.getInventory().addQtyResource(Resources.COINS, 6); //assez d'argent pour acheter les ressources
        inventory2.addQtyResource(Resources.CLAY, 3);
        assertEquals(PlayerAction.Choice.PLAY, playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getChoice());
        assertEquals(secondCard.getName(), playerWithInventory.getPlayer().getStrategy().chooseAction(playerWithInventory.getInventory(), inventory2, inventory3).getCard().getName());

    }
}
