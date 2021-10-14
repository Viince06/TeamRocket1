package board;

import board.Card.Type;
import game.Age;
import inventory.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import player.PlayerWithInventory;

import java.util.*;
import java.util.stream.Collectors;


public class Deck {
    private List<Card> cards;
    private static final Logger log = LoggerFactory.getLogger(Deck.class);

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void initialize() {

        //POUR MIEUX SE REPERER, ON FAIT TOUJOURS DANS L'ORDRE : Brown / Blue / Grey / Green / Red//

        //Brown Cards//
//LUMBER YARD//
        this.cards.add(new Card("Lumber Yard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.WOOD, 1)), null, Type.RAW, null, null));
        this.cards.add(new Card("Lumber Yard", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.WOOD, 1)), null, Type.RAW, null, null));
//STONE PIT//
        this.cards.add(new Card("Stone Pit", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.STONE, 1)), null, Type.RAW, null, null));
        this.cards.add(new Card("Stone Pit", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.STONE, 1)), null, Type.RAW, null, null));
//CLAY POOL//
        this.cards.add(new Card("Clay Pool", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.CLAY, 1)), null, Type.RAW, null, null));
        this.cards.add(new Card("Clay Pool", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.CLAY, 1)), null, Type.RAW, null, null));
//ORE VEIN//
        this.cards.add(new Card("Ore Vein", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.ORE, 1)), null, Type.RAW, null, null));
        this.cards.add(new Card("Ore Vein", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.ORE, 1)), null, Type.RAW, null, null));
//TREE FARM//
        this.cards.add(new Card("Tree Farm", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.WOOD, 1),
                new Trade(Resources.CLAY, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//EXCAVATION//
        this.cards.add(new Card("Excavation", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.STONE, 1),
                new Trade(Resources.CLAY, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//CLAY PIT//
        this.cards.add(new Card("Clay Pit", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.CLAY, 1),
                new Trade(Resources.ORE, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//TIMBER YARD//
        this.cards.add(new Card("Timber Yard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.STONE, 1),
                new Trade(Resources.WOOD, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//FOREST CAVE//
        this.cards.add(new Card("Forest Cave", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.WOOD, 1),
                new Trade(Resources.ORE, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//MINE//
        this.cards.add(new Card("Mine", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.ORE, 1),
                new Trade(Resources.STONE, 1)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));


        //Age 2//
//SAW HILL//
        this.cards.add(new Card("Saw Mill", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.WOOD, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
        this.cards.add(new Card("Saw Mill", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.WOOD, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//QUARRY//
        this.cards.add(new Card("Quarry", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.STONE, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
        this.cards.add(new Card("Quarry", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.STONE, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//BRICKYARD//
        this.cards.add(new Card("Brickyard", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.CLAY, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
        this.cards.add(new Card("Brickyard", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.CLAY, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
//FOUNDRY//
        this.cards.add(new Card("Foundry", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.ORE, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));
        this.cards.add(new Card("Foundry", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.ORE, 2)), Arrays.asList(new Trade(Resources.COINS, 1)), Type.RAW, null, null));


        //Blue Cards//
//PAWNSHOP//
        this.cards.add(new Card("Pawnshop", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, null));
        this.cards.add(new Card("Pawnshop", Age.AGE_1, 7, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, null));
//BATHS
        this.cards.add(new Card("Baths", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), Arrays.asList(new Trade(Resources.STONE, 1)), Type.CIVIL, null, Arrays.asList("Aqueduct")));
        this.cards.add(new Card("Baths", Age.AGE_1, 7, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), Arrays.asList(new Trade(Resources.STONE, 1)), Type.CIVIL, null, Arrays.asList("Aqueduct")));
//ALTAR//
        this.cards.add(new Card("Altar", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, Arrays.asList("Temple")));
        this.cards.add(new Card("Altar", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, Arrays.asList("Temple")));
//THEATER//
        this.cards.add(new Card("Theater", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, Arrays.asList("Statue")));
        this.cards.add(new Card("Theater", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Type.CIVIL, null, Arrays.asList("Statue")));
//AQUEDUCT//
        this.cards.add(new Card("Aqueduct", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)), Arrays.asList(new Trade(Resources.STONE, 3)), Type.CIVIL, "Baths", null));
        this.cards.add(new Card("Aqueduct", Age.AGE_2, 7, Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)), Arrays.asList(new Trade(Resources.STONE, 3)), Type.CIVIL, "Baths", null));
//TEMPLE//
        this.cards.add(new Card("Temple", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.GLASS, 1)), Type.CIVIL, "Altar", Arrays.asList("Pantheon")));
        this.cards.add(new Card("Temple", Age.AGE_2, 6, Arrays.asList(new Trade(Resources.VICTORY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.GLASS, 1)), Type.CIVIL, "Altar", Arrays.asList("Pantheon")));
//STATUE//
        this.cards.add(new Card("Statue", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 4)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1)), Type.CIVIL, "Theater", Arrays.asList("Gardens")));
        this.cards.add(new Card("Statue", Age.AGE_2, 7, Arrays.asList(new Trade(Resources.VICTORY_POINT, 4)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1)), Type.CIVIL, "Theater", Arrays.asList("Gardens")));
//COURTHOUSE//
        this.cards.add(new Card("Courthouse", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 4)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.LOOM, 1)), Type.CIVIL, "Scriptorium", null));
        this.cards.add(new Card("Courthouse", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.VICTORY_POINT, 4)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.LOOM, 1)), Type.CIVIL, "Scriptorium", null));
//PANTHEON//
        this.cards.add(new Card("Pantheon", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 7)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.GLASS, 1)), Type.CIVIL, "Temple", null));
        this.cards.add(new Card("Pantheon", Age.AGE_3, 6, Arrays.asList(new Trade(Resources.VICTORY_POINT, 7)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.GLASS, 1)), Type.CIVIL, "Temple", null));
//GARDENS//
        this.cards.add(new Card("Gardens", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1)), Type.CIVIL, "Statue", null));
        this.cards.add(new Card("Gardens", Age.AGE_3, 4, Arrays.asList(new Trade(Resources.VICTORY_POINT, 5)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1)), Type.CIVIL, "Statue", null));
//TOWNHALL//
        this.cards.add(new Card("TownHall", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 6)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1)), Type.CIVIL, null, null));
        this.cards.add(new Card("TownHall", Age.AGE_3, 5, Arrays.asList(new Trade(Resources.VICTORY_POINT, 6)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1)), Type.CIVIL, null, null));
        this.cards.add(new Card("TownHall", Age.AGE_3, 6, Arrays.asList(new Trade(Resources.VICTORY_POINT, 6)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1)), Type.CIVIL, null, null));
//PALACE//
        this.cards.add(new Card("Palace", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 8)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1)), Type.CIVIL, null, null));
        this.cards.add(new Card("Palace", Age.AGE_3, 7, Arrays.asList(new Trade(Resources.VICTORY_POINT, 8)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1)), Type.CIVIL, null, null));

//SENATE//
        this.cards.add(new Card("Senate", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 6)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.WOOD, 1)), Type.CIVIL, "Library", null));
        this.cards.add(new Card("Senate", Age.AGE_3, 5, Arrays.asList(new Trade(Resources.VICTORY_POINT, 6)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.WOOD, 1)), Type.CIVIL, "Library", null));


        //Grey Cards//
//LOOM//
        this.cards.add(new Card("Loom", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.LOOM, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Loom", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.LOOM, 1)), null, Type.HANDMADE, null, null));

//GLASSWORK//
        this.cards.add(new Card("Glassworks", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.GLASS, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Glassworks", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.GLASS, 1)), null, Type.HANDMADE, null, null));
//PRESS//
        this.cards.add(new Card("Press", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.PAPYRUS, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Press", Age.AGE_1, 6, Arrays.asList(new Trade(Resources.PAPYRUS, 1)), null, Type.HANDMADE, null, null));
//LOOM//
        this.cards.add(new Card("Loom", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.LOOM, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Loom", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.LOOM, 1)), null, Type.HANDMADE, null, null));
//GLASSWORK//
        this.cards.add(new Card("Glassworks", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.GLASS, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Glassworks", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.GLASS, 1)), null, Type.HANDMADE, null, null));
//PRESS//
        this.cards.add(new Card("Press", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.PAPYRUS, 1)), null, Type.HANDMADE, null, null));
        this.cards.add(new Card("Press", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.PAPYRUS, 1)), null, Type.HANDMADE, null, null));


        //Green Cards//
        //Age 1
        this.cards.add(new Card("Workshop", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)), Arrays.asList(new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Archery Range", "Laboratory")));
        this.cards.add(new Card("Workshop", Age.AGE_1, 7, Arrays.asList(new Trade(Resources.PHYSICS, 1)), Arrays.asList(new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Archery Range", "Laboratory")));
        this.cards.add(new Card("Apothecary", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)), Arrays.asList(new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, null, Arrays.asList("Stables", "Dispensary")));
        this.cards.add(new Card("Apothecary", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)), Arrays.asList(new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, null, Arrays.asList("Stables", "Dispensary")));
        this.cards.add(new Card("Scriptorium", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.WRITING, 1)), Arrays.asList(new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Courthouse", "Library")));
        this.cards.add(new Card("Scriptorium", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.WRITING, 1)), Arrays.asList(new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Courthouse", "Library")));

        this.cards.add(new Card("Dispensary", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "Apothecary", Arrays.asList("Arena", "Lodge")));
        this.cards.add(new Card("Dispensary", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "Apothecary", Arrays.asList("Arena", "Lodge")));
        this.cards.add(new Card("Laboratory", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, "Workshop", Arrays.asList("Siege Workshop", "Observatory")));
        this.cards.add(new Card("Laboratory", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, "Workshop", Arrays.asList("Siege Workshop", "Observatory")));
        this.cards.add(new Card("Library", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "Scriptorium", Arrays.asList("Senate", "University")));
        this.cards.add(new Card("Library", Age.AGE_2, 6, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "Scriptorium", Arrays.asList("Senate", "University")));
        this.cards.add(new Card("School", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Academy", "Study")));
        this.cards.add(new Card("School", Age.AGE_2, 7, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, null, Arrays.asList("Academy", "Study")));

        this.cards.add(new Card("Lodge", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, "Dispensary", null));
        this.cards.add(new Card("Lodge", Age.AGE_3, 6, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.LOOM, 1),
                        new Trade(Resources.PAPYRUS, 1)), Type.SCIENTIFIC, "Dispensary", null));
        this.cards.add(new Card("Observatory", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "Laboratory", null));
        this.cards.add(new Card("Observatory", Age.AGE_3, 7, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.GLASS, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "Laboratory", null));
        this.cards.add(new Card("University", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "Library", null));
        this.cards.add(new Card("University", Age.AGE_3, 4, Arrays.asList(new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "Library", null));
        this.cards.add(new Card("Academy", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "School", null));
        this.cards.add(new Card("Academy", Age.AGE_3, 7, Arrays.asList(new Trade(Resources.MATHEMATICS, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.GLASS, 1)), Type.SCIENTIFIC, "School", null));
        this.cards.add(new Card("Study", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "School", null));
        this.cards.add(new Card("Study", Age.AGE_3, 5, Arrays.asList(new Trade(Resources.PHYSICS, 1)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.PAPYRUS, 1),
                        new Trade(Resources.LOOM, 1)), Type.SCIENTIFIC, "School", null));


        //Red Cards
        //Age 1
        this.cards.add(new Card("Stockade", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.WOOD, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Stockade", Age.AGE_1, 7, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.WOOD, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Barracks", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.ORE, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Barracks", Age.AGE_1, 5, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.ORE, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Guard Tower", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.CLAY, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Guard Tower", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.MILITARY_POINT, 1)), Arrays.asList(new Trade(Resources.CLAY, 1)), Type.MILITARY, null, null));

        this.cards.add(new Card("Walls", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)), Arrays.asList(new Trade(Resources.STONE, 3)), Type.MILITARY, null, Arrays.asList("Fortifications")));
        this.cards.add(new Card("Walls", Age.AGE_2, 7, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)), Arrays.asList(new Trade(Resources.STONE, 3)), Type.MILITARY, null, Arrays.asList("Fortifications")));
        this.cards.add(new Card("Training Ground", Age.AGE_2, 4, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 2)), Type.MILITARY, null, Arrays.asList("Circus")));
        this.cards.add(new Card("Training Ground", Age.AGE_2, 6, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 2)), Type.MILITARY, null, Arrays.asList("Circus")));
        this.cards.add(new Card("Training Ground", Age.AGE_2, 7, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.ORE, 2)), Type.MILITARY, null, Arrays.asList("Circus")));
        this.cards.add(new Card("Stables", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.WOOD, 1)), Type.MILITARY, "Apothecary", null));
        this.cards.add(new Card("Stables", Age.AGE_2, 5, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.WOOD, 1)), Type.MILITARY, "Apothecary", null));
        this.cards.add(new Card("Archery Range", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.WOOD, 2)), Type.MILITARY, "Workshop", null));
        this.cards.add(new Card("Archery Range", Age.AGE_2, 6, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.WOOD, 2)), Type.MILITARY, "Workshop", null));

        this.cards.add(new Card("Fortifications", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 3),
                        new Trade(Resources.STONE, 1)), Type.MILITARY, "Walls", null));
        this.cards.add(new Card("Fortifications", Age.AGE_3, 7, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 3),
                        new Trade(Resources.STONE, 1)), Type.MILITARY, "Walls", null));
        this.cards.add(new Card("Circus", Age.AGE_3, 4, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 3)), Type.MILITARY, "Training Ground", null));
        this.cards.add(new Card("Circus", Age.AGE_3, 5, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 3)), Type.MILITARY, "Training Ground", null));
        this.cards.add(new Card("Circus", Age.AGE_3, 6, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.STONE, 3)), Type.MILITARY, "Training Ground", null));
        this.cards.add(new Card("Arsenal", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.WOOD, 2),
                        new Trade(Resources.LOOM, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Arsenal", Age.AGE_3, 4, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.WOOD, 2),
                        new Trade(Resources.LOOM, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Arsenal", Age.AGE_3, 7, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.ORE, 1),
                        new Trade(Resources.WOOD, 2),
                        new Trade(Resources.LOOM, 1)), Type.MILITARY, null, null));
        this.cards.add(new Card("Siege Workshop", Age.AGE_3, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)), Type.MILITARY, "Laboratory", null));
        this.cards.add(new Card("Siege Workshop", Age.AGE_3, 5, Arrays.asList(new Trade(Resources.MILITARY_POINT, 3)),
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)), Type.MILITARY, "Laboratory", null));

        this.shuffle();
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public void cardDistribution(Age currentAge, List<PlayerWithInventory> playersWithInventories, Deck deck) throws Exception {
        log.debug("Distribution des cartes");
        Deque<Card> filteredCards = deck.getCards().stream().filter(card -> card.getAge() == currentAge).collect(Collectors.toCollection(ArrayDeque::new));
        try {
            for (PlayerWithInventory playersWithInventory : playersWithInventories) {
                List<Card> cardsInHand = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    cardsInHand.add(filteredCards.pop());
                }
                playersWithInventory.getInventory().setCardsInHand(cardsInHand);
            }
        } catch (NoSuchElementException e) {
            log.error("Insufficient number of cards for Age {}", currentAge.name());
            throw new Exception(String.format("Insufficient number of cards for Age %s", currentAge.name()));

        }
    }


}