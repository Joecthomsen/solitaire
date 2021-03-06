package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

import static org.junit.jupiter.api.Assertions.*;

class MoverTest {

    @Test
    void turnOverNewCard_PlayerDeck() {

        int actual;
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H2,H12,KH,R8,R7,H3,R11");
        Match match = algorithm.checkForAnyMatch();
        assertFalse(match.match, "Confirms that the algorithm found that there is no match, and therefor must turn over a card from the player deck");
        assertEquals(11, match.fromPile, "Confirms that the algorithm found that there is no match, and therefor must turn over a card from the player deck");
//Check when divisible by 3
    //Insert next card into match object, which then automatically insert it into the player pile
        for (int i = 0 ; i < 8 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S12");
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(3 + 3*i, actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(24-(3*(i+1)), actual);
            actual = table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue();
            assertEquals(11, actual);
        }
        //Add one more card, and check if the pile is flipped over
            for (int i = 0 ; i < 6 ; i++) {
                if(match.noNextInput) {
                    match.nextPlayerCard = table.stringToCardConverter("S11");
            }
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(3 + (3 * i), actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(21 - (3 * i), actual);

        }

        for (int i = 0 ; i < 1200 ; i++) {
            if(match.noNextInput) {
                match.nextPlayerCard = table.stringToCardConverter("S11");
            }
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
//            assertEquals(3 + (3 * i), actual);
//            actual = table.getPlayerDeck_FaceDown().size();
//            assertEquals(21 - (3 * i), actual);
        }

        //Check when NOT divisible with 3
            table = new TableIO();
            algorithm = new Algorithm(table);
            move = new Mover(table);
            table.initStartTable("H2,H13,K13,R2,R2,H2,S13");
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S12");
            move.insertNextCardFromInput(match);
            match = algorithm.checkForAnyMatch();
            move.moveCard_OrPile(match);
            actual = table.getAllPiles().get(6).get(table.getAllPiles().get(6).size() - 1).getValue();
            assertEquals(12, actual);
            match.nextPlayerCard = table.stringToCardConverter("R13");
            move.insertNextCardFromInput(match);
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("R13");
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(5 + 3*i, actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(18-(3*(i)), actual);
        }
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S12");
            move.insertNextCardFromInput(match);
        }
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            if (!match.noNextInput) {
                match.nextPlayerCard = table.stringToCardConverter("H10");
                move.insertNextCardFromInput(match);
            }
        }

        for (int i = 0 ; i < 800 ; i++) {
            match = algorithm.checkForAnyMatch();
            if (!match.noNextInput) {
                match.nextPlayerCard = table.stringToCardConverter("H10");
                move.insertNextCardFromInput(match);
            }
        }

        match = algorithm.checkForAnyMatch();
        if (!match.noNextInput) {
            match.nextPlayerCard = table.stringToCardConverter("H9");
            move.insertNextCardFromInput(match);
        }
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            assertTrue(match.noNextInput);
        }
    }
    @Test
    void moveFromPlayerDeck_ToTablouPile(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H2,H13,R9,R13,R8,H3,R12");
        Match match = algorithm.checkForAnyMatch();
    //Await next input
        Card card = table.stringToCardConverter("S7");
    //Insert input into match, and then pass the match object to let the insert function handle it automatic
        match.nextPlayerCard = card;
        move.insertNextCardFromInput(match);
    //Check if there is any match in the new game state.
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        int actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 1).getValue();
        assertEquals(7, actual, "Assert that the card is actually moved to the player pile");
        boolean actualDeleted = table.getPlayerDeck_FaceUp().contains(match.nextPlayerCard);
        assertFalse(actualDeleted, "Assert that the card, is deleted from the player deck");
    //Check next card in player deck
        match.nextPlayerCard = table.stringToCardConverter("S11");
        move.insertNextCardFromInput(match);
        actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 1).getValue();
        assertEquals(7, actual);
    }
    @Test
    void moveFromPlayerDeck_ToFoundation(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H1,H12,R8,R6,R7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
    //Await next input
        Card card = table.stringToCardConverter("R12");
    //Insert input into match, and then pass the match object to let the insert function handle it automatic
        match.nextPlayerCard = card;
        move.insertNextCardFromInput(match);
    //Check if there is any match in the new game state and move it.
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("H12");
    //Move new card to player pile
        move.insertNextCardFromInput(match);
    //Check if there is any match in the new game state and move it.
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("S0");
    //Move new card to player pile
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("R9");
        move.insertNextCardFromInput(match);
        System.out.println("");
    }
    @Test
    void moveFromTablou_ToTablou(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H2,H13,R9,R6,S8,H3,R12");
        Match match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        int actual = table.getAllPiles().get(4).size();
        assertEquals(2, actual, "Assert that the size of pile 2 has increased with one");
        actual = table.getAllPiles().get(2).size();
        assertEquals(6, actual, "Assert that the size of pile 4 has decreased with one");
        actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 1).getValue();
        assertEquals(7, actual, "Assert that it is the correct card that has been moved");
        actual = table.getAllPiles().get(4).get(table.getAllPiles().get(4).size() - 1).getValue();
        assertEquals(-1, actual, "Assert that the card has been deleted in the 'move from' pile");
        match.nextPlayerCard = table.stringToCardConverter("K12");
        move.insertNextCardFromInput(match);
        assertEquals(2, table.getAllPiles().get(4).size(), "Assert that the size of pile 4 has not increased by one after flipping");
    }
    @Test
    void moveFromTablou_ToFoundation(){
        //Test that all the aces can be moved to a pile
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
    //Init start table with one ace
        table.initStartTable("H0,S0,H1,H3,S7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
        int actual = match.fromPile;
        assertEquals(0, actual, "Assert that the algorithm finds the correct match");
        actual = match.toPile;
        assertEquals(8, actual, "Assert that the ace of diamonds is matched to pile 9");
    //Move the ace to foundation
        move.moveCard_OrPile(match);
    //Check if the move has been done correctly
        assertEquals(0, table.getFundamentPiles().get(1).get(1).getValue(), "Assert that ace has been moved");
    //Reveal the card underneath the ace before
        match.nextPlayerCard = table.stringToCardConverter("K12");
        move.insertNextCardFromInput(match);
        assertEquals(11, table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).getValue(), "Assert that the king has been flipped");
    //Try with next ace
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("K12");
        move.insertNextCardFromInput(match);
    //Put more cards from player to fundament pile
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("H2");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("K12");
        move.insertNextCardFromInput(match);
        System.out.println("");
    }
    @Test
    void checkIfEmptyPile(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H1,K12,S8,S6,S7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("K2");
        move.insertNextCardFromInput(match);
        System.out.println("");
    }
    @Test
    void moveComplexPile() {

        Table table = new TableIO();
        Move move = new Mover(table);
        Algorithm algorithm = new Algorithm(table);
        Match match;
        table.initStartTable("H7,S10,H12,H4,K10,R4,R10");
        match = algorithm.checkForAnyMatch();
        assertFalse(match.match, "Assert that there is no match");
        match.nextPlayerCard = table.stringToCardConverter("S3");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        assertTrue(match.match, "Assert that there is a match from the stack");
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("H0");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        assertTrue(match.match);
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("H2");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        assertTrue(match.match);
        move.moveCard_OrPile(match);
        match = algorithm.checkForAnyMatch(); //Run again as the pile is now empty
        match.nextPlayerCard = table.stringToCardConverter("H3");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("S13");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        assertTrue(match.complex, "Assert that a complex match has been found");
        move.moveCard_OrPile(match);
        assertFalse(match.noNextInput, "Assert the noNextInput flag is false");
    //Check if next input flag is deactivated, when we don't need it.
        table = new TableIO();
        move = new Mover(table);
        algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,H5,K10,H5,R10");
        match = algorithm.checkForAnyMatch();
        assertFalse(match.match, "Assert that there is no match");
        match.nextPlayerCard = table.stringToCardConverter("S4");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        assertTrue(match.match, "Assert that there is a match from the stack");
        move.moveCard_OrPile(match);

        match.nextPlayerCard = table.stringToCardConverter("H3");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        assertTrue(match.match, "Assert that there is a match from the stack");
        move.moveCard_OrPile(match);

        match.nextPlayerCard = table.stringToCardConverter("S0");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        assertTrue(match.match);
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("S2");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        assertTrue(match.match);
        move.moveCard_OrPile(match);
        match = algorithm.checkForAnyMatch(); //Run again as the pile is now empty
        match.nextPlayerCard = table.stringToCardConverter("S3");
        move.insertNextCardFromInput(match);


        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        match.nextPlayerCard = table.stringToCardConverter("K4");
        move.insertNextCardFromInput(match);

        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);

        match = algorithm.checkForAnyMatch();

        assertTrue(match.complex, "Assert that a complex match has been found");
        move.moveCard_OrPile(match);
        assertTrue(match.noNextInput, "Assert that noInputFlag is raised in 'match'");
        System.out.printf("");
    }
    @Test
    void moveCard_OrPile() {
    }
    @Test
    void insertNextCardFromInput() {
    }
}