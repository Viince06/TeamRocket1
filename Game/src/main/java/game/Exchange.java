package game;

import board.Trade;

public class Exchange {

    private final boolean left;
    private final Trade tradeToBeDone;

    public Exchange(boolean left, Trade tradeToBeDone) {
        this.left = left;
        this.tradeToBeDone = tradeToBeDone;
    }

    public boolean isLeft() {
        return left;
    }

    public Trade getTradeToBeDone() {
        return tradeToBeDone;
    }
}
