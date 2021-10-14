package strategy;

import board.Card;
import board.Trade;
import game.Age;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static strategy.StrategyFactory.DIFFICULTY_LEVEL.*;

class TypeCounter {
    private List<Card.Type> type = new ArrayList<>();
    private int counter = 0;

    public List<Card.Type> getType() {
        return type;
    }

    public void setType(Card.Type type) {
        this.type.add(type);
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }
}


public class AdvancedMultipleStrategy extends MultipleStrategies implements IStrategy {
    private static final Logger log = LoggerFactory.getLogger(AdvancedMultipleStrategy.class);

    private Player player;
    private Age age;
    private int turn;
    private static final int evaluationTurn = 6;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) throws Exception {
        if (turn == evaluationTurn) {
            //evaluation de la liste des priorités
            //et le changer
            List<StrategyPriority> newStrategies = new ArrayList<>();
            TypeCounter green = new TypeCounter();
            green.setType(Card.Type.SCIENTIFIC);
            TypeCounter red = new TypeCounter();
            red.setType(Card.Type.MILITARY);
            TypeCounter blue = new TypeCounter();
            blue.setType(Card.Type.CIVIL);
            TypeCounter ressources = new TypeCounter();
            ressources.setType(Card.Type.RAW);
            ressources.setType(Card.Type.HANDMADE);

            //On calcule combien de cartes ont été posées selon leur type
            for (Card card : inventory.getCardsPlayed()) {
                if (card.getCardType() == green.getType().get(0)) {
                    green.incrementCounter();
                }
                if (card.getCardType() == red.getType().get(0)) {
                    red.incrementCounter();
                }
                if (card.getCardType() == blue.getType().get(0)) {
                    blue.incrementCounter();
                }
                if ((card.getCardType() == ressources.getType().get(0)) || (card.getCardType() == ressources.getType().get(1))) {
                    ressources.incrementCounter();
                }
            }

            //On regarde la couleur la plus posée
            List<TypeCounter> typeValues = new ArrayList<>();
            if (green.getCounter() > 0) {
                typeValues.add(green);
            }
            if (red.getCounter() > 0) {
                typeValues.add(red);
            }
            if (blue.getCounter() > 0) {
                typeValues.add(blue);
            }
            if (ressources.getCounter() > 0) {
                typeValues.add(ressources);
            }
            typeValues.sort(Comparator.comparing(TypeCounter::getCounter).reversed());

            for (TypeCounter tc : typeValues) {
                if (CivilStrategy.getCardType().contains(tc.getType().get(0))) {
                    newStrategies.add(new StrategyPriority(Age.AGE_1, CIVIL));
                    newStrategies.add(new StrategyPriority(Age.AGE_2, CIVIL));
                    newStrategies.add(new StrategyPriority(Age.AGE_3, CIVIL));
                }
                if (MilitaryStrategy.getCardType().contains(tc.getType().get(0))) {
                    newStrategies.add(new StrategyPriority(Age.AGE_1, MILITARY));
                    newStrategies.add(new StrategyPriority(Age.AGE_2, MILITARY));
                    newStrategies.add(new StrategyPriority(Age.AGE_3, MILITARY));
                }
                if (ResourceStrategy.getCardType().contains(tc.getType().get(0))) {
                    //Resource ne peut être jouer que a l'age 1
                    newStrategies.add(new StrategyPriority(Age.AGE_1, RESOURCE));
                }
                if (ScientificStrategy.getCardType().contains(tc.getType().get(0))) {
                    newStrategies.add(new StrategyPriority(Age.AGE_1, SCIENTIFIC));
                    newStrategies.add(new StrategyPriority(Age.AGE_2, SCIENTIFIC));
                    newStrategies.add(new StrategyPriority(Age.AGE_3, SCIENTIFIC));
                }
            }
            //ajouter EASY a la fin au cas ou il n'a qu'un strategy et
            //il peut pas jouer du coup il joue Easy et apres il ferra son choix
            newStrategies.add(new StrategyPriority(Age.AGE_1, EASY));
            newStrategies.add(new StrategyPriority(Age.AGE_2, EASY));
            newStrategies.add(new StrategyPriority(Age.AGE_3, EASY));

            this.player.setStrategyList(newStrategies);

            //pour log seulement
            String strategyNames = "";
            for (StrategyPriority st : this.player.getStrategyList()) {
                strategyNames += st.getDifficulty() + " ";
            }
            log.debug("Strategies for {} are {}", this.player.getName(), strategyNames);

        }
        MultipleStrategies multipleStrategies = new MultipleStrategies();
        multipleStrategies.setAge(this.age);
        multipleStrategies.setPlayer(this.player);
        this.player.setStrategy(multipleStrategies);

/*
        String after = "";
        for (StrategyPriority st : player.getStrategyList()) {
            after += st.getDifficulty() + " ";
        }
        log.info("List After was {}", after);
*/
        return player.getStrategy().chooseAction(inventory, inventoryLeft, inventoryRight);
    }

    @Override
    public Resources chooseReward(ArrayList<Trade> trades) {
        return trades.get(0).getResource();
    }

    @Override
    public PlayerAction chooseActionFromList(List<PlayerAction> playableAction) {
        return null;
    }

    @Override
    public String getStrategyName() {
        return "Advanced Strategy";
    }

    @Override
    public boolean maximumReached() {
        return false;
    }

}
