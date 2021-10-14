package board;

import inventory.Resources;

public class Trade {
    private final Resources resource;
    private final int quantity;

    public Trade(Resources resource, int quantity) {
        this.resource = resource;
        this.quantity = quantity;
    }

    public Resources getResource() {
        return resource;
    }

    public int getQuantity() {
        return quantity;
    }
}
