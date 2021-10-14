package inventory;

import board.Card;
import board.Wonder;

import java.util.List;

public interface IInventoryReadOnly {

    void cardPlayed(Card cardToPlay);

    List<Card> getCardsInHand();

    List<Card> getCardsPlayed();

    int getQtyResource(Resources resource);

    Wonder getWonder();
}
