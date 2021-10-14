package player;

import inventory.Inventory;

public class PlayerWithInventory {

    private Player player;
    private Inventory inventory;

    public PlayerWithInventory(Player player) {
        this.player = player;
        this.inventory = new Inventory();
    }

    public Player getPlayer() {
        return this.player;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public String toString() {
        return player.getName();
    }
}
