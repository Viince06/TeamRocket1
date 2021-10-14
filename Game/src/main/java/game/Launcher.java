package game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.Player;
import services.StatisticsHandler;
import strategy.StrategyPriority;

import java.util.ArrayList;
import java.util.List;

import static strategy.StrategyFactory.DIFFICULTY_LEVEL.*;

public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    private static final int NB_OF_PLAYERS = 3;
    private static final int NB_OF_MULTIPLE_GAMES = 1000;

    public static void main(String[] args) throws Exception {
        List<Player> listOfPlayer = new ArrayList<>();

        // Création des joueurs
        for (int i = 0; i < NB_OF_PLAYERS; i++) {
            listOfPlayer.add(new Player("Player " + (i + 1), RANDOM));
        }

        listOfPlayer.get(0).setPlayStrategy(EASY);//Player 1
        listOfPlayer.get(1).setPlayStrategy(EASY);//Player 2

        //Player 3
        //Creation de la liste des Strategies a utiliser
        List<StrategyPriority> list = new ArrayList<>();
        list.add(new StrategyPriority(Age.AGE_1, RESOURCE));
        list.add(new StrategyPriority(Age.AGE_1, SCIENTIFIC));
        list.add(new StrategyPriority(Age.AGE_2, SCIENTIFIC));
        list.add(new StrategyPriority(Age.AGE_2, CIVIL));
        list.add(new StrategyPriority(Age.AGE_2, MILITARY));
        list.add(new StrategyPriority(Age.AGE_3, SCIENTIFIC));
        list.add(new StrategyPriority(Age.AGE_3, CIVIL));
        list.add(new StrategyPriority(Age.AGE_3, MILITARY));
        //Assigner la liste des Strategies au joueur
        listOfPlayer.get(2).setStrategyList(list);

        listOfPlayer.get(1).setStrategyList(list);//Player 2
        listOfPlayer.get(1).setAmbitious(true);//Player 2

        // Lancement 1 partie OU 1000 parties
        if (args.length == 0 || "--single".equals(args[0])) {
            launchSingleGame(listOfPlayer);
        } else if ("--multiple".equals(args[0])) {
            launchMultipleGames(listOfPlayer, Launcher.NB_OF_MULTIPLE_GAMES);
        } else {
            log.error("Argument non reconnu.\nLes arguments acceptés sont :\n--single\n--multiple");
            System.exit(1);
        }

        // Envoi des stats au serveur
        StatisticsHandler.sendStat();
    }

    private static void launchSingleGame(List<Player> listOfPlayer) throws Exception {
        // Lancement du jeu
        log.info("Lancement de la partie");
        new Game(listOfPlayer).launchGame();
        log.info("Fin de la partie");
    }

    private static void launchMultipleGames(List<Player> listOfPlayer, int qty) {
        int nbErrors = 0;

        for (int i = 0; i < qty; i++) {
            try {
                new Game(listOfPlayer).launchGame();
            } catch (Exception e) {
                nbErrors++;
            }
        }

        if (nbErrors > 0) {
            log.error("{} {} pendant l'exécution des {} parties.", nbErrors, (nbErrors == 1 ? " erreur est survenue" : " erreurs sont survenues"), NB_OF_MULTIPLE_GAMES);
        } else {
            log.info("Aucune erreur n'est survenue pendant l'exécution des {} parties.", NB_OF_MULTIPLE_GAMES);
        }
    }
}
