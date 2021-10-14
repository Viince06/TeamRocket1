package board;

import inventory.IInventoryReadOnly;
import player.PlayerWithInventory;

import java.util.List;

public interface ICard {
    boolean canPay(IInventoryReadOnly inventory, List<Trade> resourcesFromExchanges);

    void pay(PlayerWithInventory player, List<Trade> resourcesFromExchanges) throws Exception;

    void giveReward(PlayerWithInventory player) throws Exception;
}
