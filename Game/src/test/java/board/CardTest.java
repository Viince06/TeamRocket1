package board;

import game.Age;
import inventory.Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;
import player.PlayerWithInventory;
import strategy.StrategyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private PlayerWithInventory playerWithInventory;

    @BeforeEach
    public void init() throws Exception {
        this.playerWithInventory = new PlayerWithInventory(new Player("Toto", StrategyFactory.DIFFICULTY_LEVEL.EASY));
    }

    // POUR MIEUX SE REPERER, ON FAIT TOUJOURS DANS L'ORDRE : Brown / Blue / Grey / Green / Red

    //Brown Cards

    @Test
    public void giveBrownReward() throws Exception {
        Card card = new Card("TestCard", Age.AGE_1, 4, Arrays.asList(new Trade(Resources.ORE, 1)), null, Card.Type.RAW, null, null);
        assertEquals(0, this.playerWithInventory.getInventory().getResources().get(Resources.ORE));
        card.giveReward(this.playerWithInventory);
        assertEquals(1, this.playerWithInventory.getInventory().getResources().get(Resources.ORE));

    }

    //Blue Cards
    @Test
    public void giveBlueReward() throws Exception {
        Card card = new Card("TestCard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.VICTORY_POINT, 1)), null, Card.Type.CIVIL, null, null);
        assertEquals(0, this.playerWithInventory.getInventory().getResources().get(Resources.VICTORY_POINT));
        card.giveReward(this.playerWithInventory);
        assertEquals(1, this.playerWithInventory.getInventory().getResources().get(Resources.VICTORY_POINT));
    }

    //Grey Cards
    @Test
    public void giveGreyReward() throws Exception {
        Card card = new Card("TestCard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.GLASS, 1)), null, Card.Type.HANDMADE, null, null);
        assertEquals(0, this.playerWithInventory.getInventory().getResources().get(Resources.GLASS));
        card.giveReward(this.playerWithInventory);
        assertEquals(1, this.playerWithInventory.getInventory().getResources().get(Resources.GLASS));
    }

    //Green Cards
    @Test
    public void giveGreenReward() throws Exception {
        Card card = new Card("TestCard", Age.AGE_1, 3, Arrays.asList(new Trade(Resources.PHYSICS, 1)), null, Card.Type.SCIENTIFIC, null, null);
        assertEquals(0, this.playerWithInventory.getInventory().getResources().get(Resources.PHYSICS));
        card.giveReward(this.playerWithInventory);
        assertEquals(1, this.playerWithInventory.getInventory().getResources().get(Resources.PHYSICS));
    }

    //Red Cards
    @Test
    public void giveRedReward() throws Exception {
        Card card = new Card("TestCard", Age.AGE_2, 3, Arrays.asList(new Trade(Resources.MILITARY_POINT, 2)), null, Card.Type.MILITARY, null, null);
        assertEquals(0, this.playerWithInventory.getInventory().getResources().get(Resources.MILITARY_POINT));
        card.giveReward(this.playerWithInventory);
        assertEquals(2, this.playerWithInventory.getInventory().getResources().get(Resources.MILITARY_POINT));
    }

    @Test
    public void canPay() {
        Card firstCard = new Card("TestCard", Age.AGE_1, 6,
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)),
                Arrays.asList(
                        new Trade(Resources.COINS, 1)), Card.Type.RAW, null, null);
        assertTrue(firstCard.canPay(playerWithInventory.getInventory())); //True, parce que on a 3 coins

        Card secondCard = new Card("TestCard", Age.AGE_1, 6,
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)),
                Arrays.asList(
                        new Trade(Resources.COINS, 4)), Card.Type.RAW, null, null);
        assertFalse(secondCard.canPay(this.playerWithInventory.getInventory())); //False, parce que on a 3 coins, il en faut 4

        this.playerWithInventory.getInventory().addQtyResource(Resources.CLAY, 1);
        this.playerWithInventory.getInventory().addQtyResource(Resources.GLASS, 3);
        Card thirdCard = new Card("test", Age.AGE_1, 6,
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 4)), Card.Type.RAW, null, null);
        assertFalse(thirdCard.canPay(this.playerWithInventory.getInventory()));

        Card fourthCard = new Card("test", Age.AGE_1, 6,
                Arrays.asList(
                        new Trade(Resources.WOOD, 1),
                        new Trade(Resources.CLAY, 3)),
                Arrays.asList(
                        new Trade(Resources.GLASS, 2),
                        new Trade(Resources.CLAY, 1),
                        new Trade(Resources.COINS, 3)), Card.Type.RAW, null, null);
        assertTrue(fourthCard.canPay(this.playerWithInventory.getInventory()));
    }


    @Test
    public void pay() throws Exception {
        List<Card> cardsInHand = new ArrayList<>();
        Card firstCard = new Card("Baths", Age.AGE_1, 3,
                Arrays.asList(
                        new Trade(Resources.VICTORY_POINT, 1)), null, Card.Type.CIVIL, null, null);
        cardsInHand.add(firstCard);
        this.playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(1, this.playerWithInventory.getInventory().getCardsInHand().size()); //Première carte bien ajoutée

        Card secondCard = new Card("Aqueduct", Age.AGE_2, 3,
                Arrays.asList(
                        new Trade(Resources.VICTORY_POINT, 5)),
                Arrays.asList(
                        new Trade(Resources.STONE, 3)), Card.Type.CIVIL, "Baths", null);
        cardsInHand.add(secondCard);
        this.playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(2, this.playerWithInventory.getInventory().getCardsInHand().size()); //Deuxième carte bien ajoutée

        firstCard.pay(this.playerWithInventory);
        secondCard.pay(this.playerWithInventory);
        assertEquals(0, this.playerWithInventory.getInventory().getCardsInHand().size()); //Payement effectué par evolution!
        assertEquals(2, this.playerWithInventory.getInventory().getCardsPlayed().size());

    }

    @Test
    public void hasNextCard() {
        List<Card> cardsInHand = new ArrayList<>();
        Card firstCard = new Card("Baths", Age.AGE_1, 3,
                Arrays.asList(
                        new Trade(Resources.VICTORY_POINT, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1)), Card.Type.CIVIL, null,
                Arrays.asList("Aqueduct"));
        cardsInHand.add(firstCard);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(1, playerWithInventory.getInventory().getCardsInHand().size());

        Card secondCard = new Card("Aqueduct", Age.AGE_2, 3,
                Arrays.asList(
                        new Trade(Resources.VICTORY_POINT, 5)),
                Arrays.asList(
                        new Trade(Resources.STONE, 3)), Card.Type.CIVIL, "Baths", null);
        cardsInHand.add(secondCard);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(2, playerWithInventory.getInventory().getCardsInHand().size());

        //Case 1: une seule carte évolutive disponible
        assertTrue(firstCard.hasNextCard(playerWithInventory));

        Card thirdCard = new Card("Scriptorium", Age.AGE_1, 3,
                Arrays.asList(
                        new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.PAPYRUS, 1)), Card.Type.SCIENTIFIC, null,
                Arrays.asList("Courthouse", "Library"));
        cardsInHand.add(thirdCard);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(3, playerWithInventory.getInventory().getCardsInHand().size());

        Card fourthCard = new Card("Library", Age.AGE_2, 3,
                Arrays.asList(
                        new Trade(Resources.WRITING, 1)),
                Arrays.asList(
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.STONE, 1),
                        new Trade(Resources.LOOM, 1)), Card.Type.SCIENTIFIC, "Scriptorium",
                Arrays.asList("Senate", "University"));
        cardsInHand.add(fourthCard);
        playerWithInventory.getInventory().setCardsInHand(cardsInHand);
        assertEquals(4, playerWithInventory.getInventory().getCardsInHand().size());

        //Case 2: deux cartes évolutive disponibles, choisir la deuxième
        assertTrue(thirdCard.hasNextCard(playerWithInventory));

        //Case 3: aucune carte disponible
        assertFalse(fourthCard.hasNextCard(playerWithInventory));
    }
}