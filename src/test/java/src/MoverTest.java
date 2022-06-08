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
        table.initStartTable("H1,H12,KH,R8,R7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
        assertFalse(match.match, "Confirms that the algorithm found that there is no match, and therefor must turn over a card from the player deck");
        assertEquals(11, match.fromPile, "Confirms that the algorithm found that there is no match, and therefor must turn over a card from the player deck");
    //Check when divisible by 3
    //Insert next card into match object, which then automatically insert it into the player pile
        for (int i = 0 ; i < 8 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("R12");
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(3 + 3*i, actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(24-(3*(i+1)), actual);
            actual = table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue();
            assertEquals(12, actual);
        }
        //Add one more card, and check if the pile is flipped over
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S12");
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(3, actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(21, actual);

        //Check when NOT divisible with 3
            table = new TableIO();
            algorithm = new Algorithm(table);
            move = new Mover(table);
            table.initStartTable("H1,H12,KH,R8,R7,H2,R11");
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S10");
            move.insertNextCardFromInput(match);
            match = algorithm.checkForAnyMatch();
            move.moveCard_OrPile(match);
            actual = table.getAllPiles().get(6).get(table.getAllPiles().get(6).size() - 1).getValue();
            assertEquals(10, actual);
            match.nextPlayerCard = table.stringToCardConverter("R12");
            move.insertNextCardFromInput(match);
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("R12");
            move.insertNextCardFromInput(match);
            actual = table.getPlayerDeck_FaceUp().size();
            assertEquals(5 + 3*i, actual);
            actual = table.getPlayerDeck_FaceDown().size();
            assertEquals(18-(3*(i)), actual);
        }
        //TODO assert this.
        for (int i = 0 ; i < 7 ; i++) {
            match = algorithm.checkForAnyMatch();
            match.nextPlayerCard = table.stringToCardConverter("S12");
            move.insertNextCardFromInput(match);
            //actual = table.getPlayerDeck_FaceUp().size();
            //assertEquals(5 + 3*i, actual);
            //actual = table.getPlayerDeck_FaceDown().size();
            //assertEquals(18-(3*(i)), actual);
        }
        System.out.printf("");
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("H12");
        move.insertNextCardFromInput(match);
        System.out.printf("");
    }

    @Test
    void moveFromPlayerDeck_ToTablouPile(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H1,H12,R8,R12,R7,H2,R11");
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
        Card card = table.stringToCardConverter("S0");
    //Insert input into match, and then pass the match object to let the insert function handle it automatic
        match.nextPlayerCard = card;
        move.insertNextCardFromInput(match);
    //Check if there is any match in the new game state and move it.
        match = algorithm.checkForAnyMatch();
    //Move that new match
        move.moveCard_OrPile(match);
    //Test if it is moved is correctly
        int actual = table.getFundamentPiles().get(3).get(table.getFundamentPiles().get(3).size() - 1).getValue();
        assertEquals(0, actual, "Assert that the card is moved to the foundation pile");
        actual = table.getPlayerDeck_FaceUp().size();
        assertEquals(2, actual, "Assert that the size of the player deck is decremented after moving a card to foundation pile");
        actual = table.getPlayerDeck_FaceDown().size();
        assertEquals(21, actual, "Assert that the player deck index is decremented correctly, when a card is moved to the foundation pile");
    }

    @Test
    void moveFromTablou_ToTablou(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H1,H12,R8,R6,S7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        int actual = table.getAllPiles().get(4).size();
        assertEquals(4, actual, "Assert that the size of pile 4 has increased with one");
        actual = table.getAllPiles().get(3).size();
        assertEquals(3, actual, "Assert that the size of pile 3 has decreased with one");
        actual = table.getAllPiles().get(4).get(table.getAllPiles().get(4).size() - 1).getValue();
        assertEquals(6, actual, "Assert that it is the correct card that has been moved");
        actual = table.getAllPiles().get(3).get(table.getAllPiles().get(3).size() - 1).getValue();
        assertEquals(-1, actual, "Assert that the card has been deleted in the 'move from' pile");
    //Check if the card mover moves the two cards to pile 2
    //First tell which card has been revealed in pile 3.
        match.nextPlayerCard = table.stringToCardConverter("H12");  //Just insert another king in order NOT to find a match with the new card
    //Now insert that card
        move.insertNextCardFromInput(match);
    //Check if the correct card has been inserted.
        actual = table.getAllPiles().get(3).size();
        assertEquals(3, actual, "Assert that the size of pile 3 has NOT incremented with one, when turning over the card in the pile");
    //Check if it can move more than one card (in this case form 4 to 2)
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        //TODO Make assessment - it is checked correct in debugger
        System.out.printf("");




//        assertEquals(12, actual, "Assert that it is the correct card, that has been turn over in pile 3");
//    //Let the algorithm check, if there is any match
//        match = algorithm.checkForAnyMatch();
//        actual = match.fromPile;
//        assertTrue(match.match, "Assert that the match boolean is true");
//        assertEquals(4, actual, "Assert if the algorithm find the match from pile 4.");
//        actual = match.toPile;
//        assertEquals(2, actual);
//    //Move pile 4 to pile 2
//        move.moveCard_OrPile(match);
//        actual = table.getAllPiles().get(2).size();
//        assertEquals(7, actual, "Assert if the size of pile 2 has increased by 2");
//        actual = table.getAllPiles().get(4).size();
//        assertEquals(2, actual, "Assert if size og pile 4 has decreased with 2");
//        actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 3).getValue();
//        assertEquals(8, actual, "Assert that the original card in the pile, is still the top card");
//        actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 2).getValue();
//        assertEquals(7, actual, "Assert that the seven is moved to the new pile");
//        actual = table.getAllPiles().get(2).get(table.getAllPiles().get(2).size() - 1).getValue();
//        assertEquals(6, actual, "Assert that the six is moved to the new pile");
    }

    @Test
    void moveFromTablou_ToFoundation(){
        //Test that all the aces can be moved to a pile
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
    //Init start table with one ace
        table.initStartTable("H0,S0,K0,R0,S7,H2,R11");
        Match match = algorithm.checkForAnyMatch();
        int actual = match.fromPile;
        assertEquals(0, actual, "Assert that the algorithm finds the correct match");
        actual = match.toPile;
        assertEquals(8, actual, "Assert that the ace of diamonds is matched to pile 9");
    //Move the ace to foundation
        move.moveCard_OrPile(match);
    //Check if the move has been done correctly
        actual = table.getFundamentPiles().get(9-8).get(table.getFundamentPiles().get(9-8).size() - 1).getValue();
        assertEquals(0, actual, "Check if the ace has been moved to pile 8 (or pile 8 in the foundation list)");
        actual = table.getAllPiles().get(0).size();
        assertEquals(6, actual, "Assert that the size of the tablou pile has been deducted");
        actual = table.getAllPiles().get(0).get(table.getAllPiles().get(3).size() - 1).getValue();
        assertEquals(-1, actual, "Assert that the value of the not yet revealed card is -1");
    //Turn over next card
        match.nextPlayerCard = table.stringToCardConverter("S12");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        actual = match.fromPile;
        assertEquals(1, actual, "Assert that the correct match has been found");
        move.moveCard_OrPile(match);
        actual = table.getFundamentPiles().get(3).get(1).getValue();
        assertEquals(0, actual);
    //Turn over next card
        match.nextPlayerCard = table.stringToCardConverter("S12");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        actual = match.fromPile;
        assertEquals(2, actual, "Assert that the correct match has been found");
        move.moveCard_OrPile(match);
        actual = table.getFundamentPiles().get(0).get(1).getValue();
        assertEquals(0, actual);
        System.out.printf("");

    //Turn over next card
        match.nextPlayerCard = table.stringToCardConverter("S12");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        actual = match.fromPile;
        assertEquals(3, actual, "Assert that the correct match has been found");
        move.moveCard_OrPile(match);
        actual = table.getFundamentPiles().get(3).get(1).getValue();
        assertEquals(0, actual);
        System.out.printf("");
    }

    @Test
    void moveComplexPile() {
    }

    @Test
    void moveCard_OrPile() {
    }

    @Test
    void insertNextCardFromInput() {
    }
}