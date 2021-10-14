package player;


import board.Card;
import game.Exchange;

import java.util.ArrayList;
import java.util.List;

public class PlayerAction {

    private Choice choice;
    private Card card;
    private List<Exchange> listOfExchange = new ArrayList<>();

    public enum Choice {
        PLAY,
        SACRIFICE,
        WONDER
    }

    public PlayerAction(Card card, Choice choice) {
        this.card = card;
        this.choice = choice;
    }

    public PlayerAction(Card card, List<Exchange> listOfExchange) {
        this.card = card;
        this.choice = Choice.PLAY;
        this.listOfExchange = listOfExchange;
    }

    public PlayerAction(Card card, List<Exchange> listOfExchange, Choice choice) { //Constructeur pour jouer la merveille
        this.card = card;
        this.choice = choice;
        this.listOfExchange = listOfExchange;

    }

    public Choice getChoice() {
        return choice;
    }

    public Card getCard() {
        return card;
    }


    public List<Exchange> getExchangeList() {
        return listOfExchange;
    }
}
