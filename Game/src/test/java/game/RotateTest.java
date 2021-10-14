package game;
import board.Card;
import board.Trade;
import inventory.Resources;
import org.junit.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RotateTest {

    List<PlayerWithInventory> listPlayer = new ArrayList<>();
    Card card1 = new Card("Lumber Yard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.WOOD, 1)), null, Card.Type.RAW, null, null);
    Card card2 = new Card("Stone Pit", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.STONE, 1)), null, Card.Type.RAW, null, null);
    Card card3 = new Card("Clay Pool", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.CLAY, 1)), null, Card.Type.RAW, null, null);
    List<Card> d1 = new ArrayList<>();
    List<Card> d2 = new ArrayList<>();
    List<Card> d3 = new ArrayList<>();


    @Test
    public void giveCardsTest() throws Exception {
        for(int i = 0; i < 3; i++){
            listPlayer.add(new PlayerWithInventory(new Player("Player "+ (i+1), StrategyFactory.DIFFICULTY_LEVEL.EASY)));
        }
        d1.add(card1);
        d2.add(card2);
        d3.add(card3);
        listPlayer.get(0).getInventory().setCardsInHand(d1);
        listPlayer.get(1).getInventory().setCardsInHand(d2);
        listPlayer.get(2).getInventory().setCardsInHand(d3);

        Card c1 = listPlayer.get(0).getInventory().getCardsInHand().get(0);
        Card c2 = listPlayer.get(1).getInventory().getCardsInHand().get(0);
        Card c3 = listPlayer.get(2).getInventory().getCardsInHand().get(0);

        //On fait tourner les cartes dans le sens horaire
        Rotate.giveCards(true, listPlayer);
        assertEquals(c3.getName(),listPlayer.get(0).getInventory().getCardsInHand().get(0).getName());
        assertEquals(c1.getName(),listPlayer.get(1).getInventory().getCardsInHand().get(0).getName());
        assertEquals(c2.getName(),listPlayer.get(2).getInventory().getCardsInHand().get(0).getName());


        //On fait tourner les cartes dans le sens anti-horaire, elles doivent reprendre leur place originale
        Rotate.giveCards(false, listPlayer);
        assertEquals(c1.getName(),listPlayer.get(0).getInventory().getCardsInHand().get(0).getName());
        assertEquals(c2.getName(),listPlayer.get(1).getInventory().getCardsInHand().get(0).getName());
        assertEquals(c3.getName(),listPlayer.get(2).getInventory().getCardsInHand().get(0).getName());
    }

}
