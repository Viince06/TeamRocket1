package cucumbertests;

import board.Trade;
import inventory.Resources;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeStepDefs implements En {

    Trade trade;

    public TradeStepDefs() {

        Given("a trade with {int} resource WOOD is created",
                (Integer quantity) -> {
                    trade = new Trade(Resources.WOOD, quantity.intValue());
                });


        When("You ask for the resource WOOD then the quantity should be {int}",
                (Integer qty) -> {
                    assertEquals(qty.intValue(), trade.getQuantity());
                });

    }


}