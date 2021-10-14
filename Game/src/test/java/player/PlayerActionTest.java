package player;

import board.Card;
import board.Trade;
import game.Age;
import inventory.Resources;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerActionTest {

    @Test
    public void testCard() {
        Card card = new Card("Test", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.ORE, 1)), null, Card.Type.RAW, null, null);
        ArrayList<Card> choice = new ArrayList<>();
        choice.add(card);

        PlayerAction action = new PlayerAction(choice.get(0), PlayerAction.Choice.PLAY);

        assertEquals(card, action.getCard());
        assertEquals(PlayerAction.Choice.PLAY, action.getChoice());
    }
}