package game;

import board.Card;
import board.Deck;
import board.DeckWonders;
import board.Trade;
import inventory.IInventoryReadOnly;
import inventory.PointsCalculator;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import player.PlayerAction;
import player.PlayerWithInventory;
import services.StatisticsHandler;
import strategy.AdvancedMultipleStrategy;
import strategy.MultipleStrategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Game {

    private Deck deck;
    private DeckWonders deckWonders;
    private Referee referee;
    private LinkedList<PlayerWithInventory> playersWithInventories;
    private static final Logger log = LoggerFactory.getLogger(Game.class);


    public Game(List<Player> list) {
        // Création de la partie
        referee = new Referee();

        // Création du paquet de carte
        deck = new Deck();
        deck.initialize();

        // Création de la liste des merveilleLs
        deckWonders = new DeckWonders();
        deckWonders.initialize();


        //Création des joueurs et de leurs inventaires et leur deck correspondant
        playersWithInventories = new LinkedList<>();

        for (Player player : list) {
            playersWithInventories.add(new PlayerWithInventory(player));
        }
    }

    /**
     * A n'utiliser que pour les tests !!!
     */
    public List<PlayerWithInventory> getPlayersWithInventories() {
        return playersWithInventories;
    }

    /**
     * Game interroge Player en lui envoyant une copie de son inventaire
     * Avec ça Player décide d'exécuter une action et renvoie à Game un objet Choice qui contient l'action et la carte que le joueur aura choisie
     * Game récupère ce Choice et l'envoie à Referee
     * Game récupère la réponse de Referee, si c'est oui l'action est exécutée par Game, si c'est non on revient à l'étape 1 et on boucle
     */
    public void launchGame() throws Exception {
        //Répetition de ce qui suit pour chaque Age
        deckWonders.wondersDistribution(playersWithInventories);

        for (Age age : Age.values()) {
            log.debug("====== AGE {} ======", age);

            // Distribution des cartes
            deck.cardDistribution(age, playersWithInventories, deck);

            for (PlayerWithInventory playerWithInventory : playersWithInventories) {
                List<String> cardNames = new ArrayList<>();
                for (Card cardGiven : playerWithInventory.getInventory().getCardsInHand()) {
                    cardNames.add(cardGiven.getName());
                }
                log.debug("le {} a reçu les cartes {}", playerWithInventory.getPlayer().getName(), cardNames);
            }

            int turn = 0;
            while (twoOrMoreCardsInHandLeft(playersWithInventories)) {
                log.debug("== TURN {} ==", ++turn);
                boolean playable;
                PlayerAction choice;
                List<PlayerAction> choiceList = new ArrayList<>();
                //Les joueurs choisissent le coup à jouer indépendamment du choix des autres joueurs
                for (int i = 0; i < playersWithInventories.size(); i++) {
                    PlayerWithInventory player = playersWithInventories.get(i);
                    IInventoryReadOnly leftPlayerInventory = i == 0 ? playersWithInventories.getLast().getInventory() : playersWithInventories.get(i - 1).getInventory();
                    IInventoryReadOnly rightPlayerInventory = (i == playersWithInventories.size() - 1) ? playersWithInventories.getFirst().getInventory() : playersWithInventories.get(i + 1).getInventory();
                    do {
                        choice = getActionChosen(player, age, turn, leftPlayerInventory, rightPlayerInventory);
                        playable = referee.isChoicePlayable(choice, playersWithInventories.get(i).getInventory(), leftPlayerInventory, rightPlayerInventory);
                        log.debug("Le joueur '{}' choisi d'exécuter l'action {} sur la carte '{}', l'arbitre {}", player.getPlayer().getName(), choice.getChoice(), choice.getCard().getName(), playable ? "accepte" : "refuse");
                    } while (!playable);
                    choiceList.add(choice); //le choix est stocké pour être joué juste après simultanément

                }
                cardExchange(choiceList);
                Rotate.giveCards(age.getSens(), playersWithInventories);

            }
            // Défausse de la dernière carte
            for (PlayerWithInventory player : playersWithInventories) {
                player.getInventory().setCardsSacrificed(player.getInventory().getCardsInHand().get(0));
            }

            //Calcul des victoires militaire pour l'age actuel et ajout des points dans l'inventaire
            Map<PlayerWithInventory, Integer> ptsMilitaire = PointsCalculator.calculMilitaryPoints(playersWithInventories, age);
            for (PlayerWithInventory player : playersWithInventories) {
                player.getInventory().addQtyResource(Resources.MILITARY_VICTORY_POINT, ptsMilitaire.get(player));
                log.debug("Le joueur '{}' a {} points de victoire militaire au tour {}", player.getPlayer().getName(), player.getInventory().getQtyResource(Resources.MILITARY_VICTORY_POINT), age);
            }

        }
        defineWinner();
    }

    private PlayerAction getActionChosen(PlayerWithInventory player, Age age, int turn, IInventoryReadOnly leftPlayerInventory, IInventoryReadOnly rightPlayerInventory) throws Exception {
        if ((player.getPlayer().getStrategyList().size() > 0) && !player.getPlayer().isAmbitious()) {
            MultipleStrategies multipleStrategies = new MultipleStrategies();
            multipleStrategies.setAge(age);
            multipleStrategies.setPlayer(player.getPlayer());
            player.getPlayer().setStrategy(multipleStrategies);
        }
        if ((player.getPlayer().getStrategyList().size() > 0) && player.getPlayer().isAmbitious()) {
            AdvancedMultipleStrategy advancedMultipleStrategy = new AdvancedMultipleStrategy();
            advancedMultipleStrategy.setPlayer(player.getPlayer());
            advancedMultipleStrategy.setTurn(turn);
            advancedMultipleStrategy.setAge(age);
            player.getPlayer().setStrategy(advancedMultipleStrategy);
        }
        return player.getPlayer().getStrategy().chooseAction(player.getInventory(), leftPlayerInventory, rightPlayerInventory);
    }


    /**
     * Permet l'échange entre les joueurs ainsi que d'appliquer le choix d'action du joueur
     */
    private void cardExchange(List<PlayerAction> listOfChoices) throws Exception {

        for (int i = 0; i < listOfChoices.size(); i++) {
            PlayerAction currentAction = listOfChoices.get(i);
            PlayerWithInventory player = playersWithInventories.get(i);
            player.getInventory().cardPlayed(listOfChoices.get(i).getCard());
            for (Exchange exchange : currentAction.getExchangeList()) {
                Trade trade = exchange.getTradeToBeDone();
                boolean isLeft = exchange.isLeft();
                PlayerWithInventory playerTarget;
                if (isLeft) {
                    playerTarget = i == 0 ? playersWithInventories.getLast() : playersWithInventories.get(i - 1);
                } else {
                    playerTarget = i == listOfChoices.size() - 1 ? playersWithInventories.getFirst() : playersWithInventories.get(i + 1);
                }
                int cost = (trade.getQuantity() * 2);

                player.getInventory().addQtyResource(Resources.COINS, (-1 * cost));
                playerTarget.getInventory().addQtyResource(Resources.COINS, cost);

                log.debug("Echange de ressource effectué entre '{}' et '{}' ({} {} contre {} golds)", player.getPlayer().getName(), playerTarget.getPlayer().getName(), trade.getQuantity(), trade.getResource(), cost);
            }
            playerChoice(currentAction, player);
        }
    }

    /**
     * Choice of playing a card, sacrificing a card or choosing wonder
     */
    private void playerChoice(PlayerAction currentAction, PlayerWithInventory player) throws Exception {
        switch (currentAction.getChoice()) {
            case PLAY:
                List<Trade> resourcesFromExchanges = currentAction.getExchangeList().stream().map(Exchange::getTradeToBeDone).collect(Collectors.toList());
                currentAction.getCard().pay(player, resourcesFromExchanges);
                break;

            case SACRIFICE:
                player.getInventory().setCardsSacrificed(currentAction.getCard());
                player.getInventory().addQtyResource(Resources.COINS, 3);
                break;

            case WONDER:
                player.getInventory().setCardsUsedForWonder(currentAction.getCard());
                player.getInventory().getWonder().pay(player);
                break;

            default:
                throw new Exception("Error Occurred");
        }
    }

    /**
     * Vérifie si toutes les mains des joueurs sont vide ou pas
     * return true si il y a au moins une main qui n'est pas vide
     * return false si toutes les mains sont vide
     */
    private boolean twoOrMoreCardsInHandLeft(LinkedList<PlayerWithInventory> playersWithInventories) {
        for (PlayerWithInventory playersWithInventory : playersWithInventories) {
            if (playersWithInventory.getInventory().getCardsInHand().size() >= 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trouve le gagnant, l'affiche et stocke les infos à envoyer au serveur
     */
    private void defineWinner() {
        //Affichage du gagnant et du nombre de points en fin de partie pour ce joueur
        List<PlayerWithInventory> winners = referee.getWinner(playersWithInventories);
        String winnerNames = winners.stream().map(p -> p.getPlayer().getName()).collect(Collectors.joining(" et "));
        if (winners.size() > 1) {
            log.info("Les joueurs {} sont a égalité", winnerNames);
        } else {
            log.info("{} gagne la partie", winnerNames);
        }
        //Ajout des stats
        StatisticsHandler.addStat(this.playersWithInventories);
    }
}
