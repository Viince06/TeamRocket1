package strategy;

import board.Trade;
import game.Age;
import inventory.IInventoryReadOnly;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.PlayerAction;

import java.util.ArrayList;
import java.util.List;

public class MultipleStrategies extends PlayStrategy implements IStrategy {

    private static final Logger log = LoggerFactory.getLogger(MultipleStrategies.class);

    private Player player;
    private Age age;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }


    @Override
    public PlayerAction chooseAction(IInventoryReadOnly inventory, IInventoryReadOnly inventoryLeft, IInventoryReadOnly inventoryRight) throws Exception {
        PlayerAction play;
        //liste contenant les strategies du joueur
        List<StrategyPriority> listPlayer = this.player.getStrategyList();
        //liste qui contiendra les strategies dont le joueur est deja au max par rapport a ses adversaires
        List<StrategyPriority> maxReachedList = new ArrayList<>();
        //contiendra les PlayerAction (actions (jouer ou sacrifier) a faire et sur quelle carte)
        List<PlayerAction> actionLi = new ArrayList<>();
        //contiendra les PlayerAction (actions (jouer ou sacrifier) a faire et sur quelle carte) des Strategies dont le joueur est au max par rapport a ses adversaires
        List<PlayerAction> maxAction = new ArrayList<>();
        for (StrategyPriority st : listPlayer) {
            //si la strategie dans la liste correspond a l'age actuel
            if (st.getAge().getAge() == this.age.getAge()) {
                player.setStrategy(st.getDifficulty());
                //on obtient la carte a jouer du strategie dans la liste
                play = player.getStrategy().chooseAction(inventory, inventoryLeft, inventoryRight);
                /**
                 * Si le joueur est deja au maximum pour cette strategie,
                 * par exemple si le joueur joue Militaire et il a 5 points Militaire de plus que ses adversaires
                 * on ajoute la strategie Militaire dans la liste maxReachedList
                 * et on ajoute la PlayerAction du strategie Militaire dans la liste maxAction
                 */
                if (player.getStrategy().maximumReached()) {
                    log.debug("Maximum points reached for {}", player.getStrategy().getStrategyName());
                    maxReachedList.add(st);
                    log.debug("Play {} on card {}", play.getChoice(), play.getCard().getName());
                    maxAction.add(play);
                } else if (play.getChoice() == PlayerAction.Choice.SACRIFICE) {
                    //si le joueur veut sacrifier une carte on garde pas cette PlayerAction dans la liste actionLi
                    log.debug("{} is using {} and wants to SACRIFICE, changing Strategy to next in the list", player.getName(), player.getStrategy().getStrategyName());
                } else {
                    //sinon la PlayerAction est garder dans la liste actionLi et on sort de la boucle pour les autres strategies dans la liste
                    log.debug("{} is using {}", player.getName(), player.getStrategy().getStrategyName());
                    actionLi.add(play);
                    break;
                }
            }
        }
        /**
         * Pour chaque strategie dont le joueur est au niveau maximum,
         *  1) on enleve la strategie de sa liste de strategies
         *  2) et on le remet au dernier position
         */
        for (StrategyPriority st : maxReachedList) {
            listPlayer.remove(st);
            listPlayer.add(st);
        }

        log.debug("List was {}", actionLi.size());
        /**
         * Si le joueur est au maximum dans toutes les strategies dans sa liste,
         * c'est bete de sacrifier une carte alors on joue une carte de la liste des PlayerAction maxAction
         */

        actionLi.addAll(maxAction);
        log.debug("List became {}", actionLi.size());

        //si je peux jouer aucune carte alors je rajoute une sacrifice dans la list actionLi au dernier position
        actionLi.add(super.sacrificeCard(inventory.getCardsInHand().get(0)));
        //je retourne la premiere action de la liste actionLi
        return actionLi.get(0);
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
        return "Multiple Strategies";
    }

    @Override
    public boolean maximumReached() {
        return false;
    }

}
