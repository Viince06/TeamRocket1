package board;

import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerWithInventory;

import java.util.*;

public class DeckWonders {

    private final List<Wonder> wonders;

    private static final Logger log = LoggerFactory.getLogger(DeckWonders.class);

    public DeckWonders() {
        this.wonders = new ArrayList<>();
    }

    public List<Wonder> getWonders() {
        return wonders;
    }

    public void initialize() {

        this.wonders.add(new Wonder("RHODOS_A", new Trade(Resources.ORE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.CLAY, 3)),
                        Arrays.asList(new Trade(Resources.ORE, 4)))));

        this.wonders.add(new Wonder("RHODOS_B", new Trade(Resources.ORE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3), new Trade(Resources.MILITARY_POINT, 1), new Trade(Resources.COINS, 3)),
                        Arrays.asList(new Trade(Resources.MILITARY_POINT, 1), new Trade(Resources.VICTORY_POINT, 4), new Trade(Resources.COINS, 4))),
                Arrays.asList(Arrays.asList(new Trade(Resources.STONE, 3)),
                        Arrays.asList(new Trade(Resources.ORE, 4)))));

        this.wonders.add(new Wonder("EPHESOS_A", new Trade(Resources.PAPYRUS, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.COINS, 9)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.STONE, 2)),
                        Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.PAPYRUS, 2)))));

        this.wonders.add(new Wonder("EPHESOS_B", new Trade(Resources.PAPYRUS, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3), new Trade(Resources.COINS, 4)),
                        Arrays.asList(new Trade(Resources.COINS, 4), new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 5), new Trade(Resources.COINS, 4))),
                Arrays.asList(Arrays.asList(new Trade(Resources.STONE, 2)),
                        Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.PAPYRUS, 1), new Trade(Resources.GLASS, 1), new Trade(Resources.LOOM, 1)))));

        this.wonders.add(new Wonder("GIZA_A", new Trade(Resources.STONE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.STONE, 2)),
                        Arrays.asList(new Trade(Resources.WOOD, 3)),
                        Arrays.asList(new Trade(Resources.STONE, 4)))));

        this.wonders.add(new Wonder("GIZA_B", new Trade(Resources.STONE, 1),
                Arrays.asList(Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)),
                        Arrays.asList(new Trade(Resources.VICTORY_POINT, 7))),
                Arrays.asList(Arrays.asList(new Trade(Resources.WOOD, 2)),
                        Arrays.asList(new Trade(Resources.STONE, 3)),
                        Arrays.asList(new Trade(Resources.CLAY, 3)),
                        Arrays.asList(new Trade(Resources.STONE, 4), new Trade(Resources.PAPYRUS, 1)))));


        this.shuffle();
    }

    private void shuffle() {
        Collections.shuffle(wonders);
    }


    /**
     * Distribue les Merveilles aux joueurs, pour le moment les joueurs ne peuvent pas choisir la merveille avec laquelle ils jouent
     */
    public void wondersDistribution(List<PlayerWithInventory> playersWithInventories) {
        log.debug("Choix des Merveilles :");
        Deque<Wonder> allWonders = new ArrayDeque<>(getWonders());
        for (PlayerWithInventory playerWithInventory : playersWithInventories) {
            Wonder wonder = allWonders.pop();
            playerWithInventory.getInventory().setWonder(wonder);
            log.debug("{} choisi la merveille '{}'", playerWithInventory.getPlayer().getName(), wonder.getName());
        }

    }
}
