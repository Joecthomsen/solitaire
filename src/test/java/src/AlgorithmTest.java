package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {

    //TODO Test for king to match empty pile
    @Test
    void sortList() {

        int actualValue;
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);

        List<List<Card>> underTest = new ArrayList<>();

        List<Card> pileOne = new ArrayList<>();
        List<Card> pileTwo = new ArrayList<>();
        List<Card> pileThree = new ArrayList<>();
        List<Card> pileFour = new ArrayList<>();
        List<Card> pileFive = new ArrayList<>();
        List<Card> pileSix = new ArrayList<>();
        List<Card> pileSeven = new ArrayList<>();

        pileOne.add(new Card(1,1,12,true,0));
        pileTwo.add(new Card(1,1,7,true,1));
        pileThree.add(new Card(1,1,2,true,2));
        pileFour.add(new Card(1,1,6,true,3));

        pileFive.add(new Card(1,1,8,true,4));
        pileFive.add(new Card(0,0,7,true,4));
        pileFive.add(new Card(1,1,6,true,4));
        pileFive.add(new Card(0,0,5,true,4));
        pileFive.add(new Card(1,1,4,true,4));
        pileFive.add(new Card(0,0,3,true,4));
        pileFive.add(new Card(1,1,2,true,4));

        pileSix.add(new Card(1,1,11,true,5));
        pileSeven.add(new Card(1,1,3,true,6));

        underTest.add(pileOne);
        underTest.add(pileTwo);
        underTest.add(pileThree);
        underTest.add(pileFour);
        underTest.add(pileFive);
        underTest.add(pileSix);
        underTest.add(pileSeven);

        List<List<Card>> sortedList = algorithm.sortList(underTest);

        actualValue = sortedList.get(0).get(0).getValue();
        assertEquals(2, actualValue);

        actualValue = sortedList.get(1).get(0).getValue();
        assertEquals(3, actualValue);

        actualValue = sortedList.get(2).get(0).getValue();
        assertEquals(6, actualValue);

        actualValue = sortedList.get(3).get(0).getValue();
        assertEquals(7, actualValue);

        actualValue = sortedList.get(4).get(0).getValue();
        assertEquals(8, actualValue);

        actualValue = sortedList.get(5).get(0).getValue();
        assertEquals(11, actualValue);

        actualValue = sortedList.get(6).get(0).getValue();
        assertEquals(12, actualValue);

    }

    @Test
    void checkForEmptyTablouPile(){

        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
    //Init start table with one ace
        table.initStartTable("H7,S10,H10,S2,K10,S12,S0");
        Match match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        int actual = table.getAllPiles().get(6).size();
        assertEquals(0, actual, "Assert that pile 6 is empty");
        match.nextPlayerCard = table.stringToCardConverter("E");
        move.insertNextCardFromInput(match);
        assertEquals(0, table.getAllPiles().get(6).size(), "Assert that the pile is still empty");
        match.nextPlayerCard = table.stringToCardConverter("S2");
        move.insertNextCardFromInput(match);
        assertEquals(0, table.getAllPiles().get(6).size(), "Assert that the pile is still even when trying to insert a real card in match object");
    }

    @Test
    void kingToMatchEmptyPile(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
    //Init start table with one ace
        table.initStartTable("H7,K2,H2,S2,K10,S11,S0");
        Match match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        int actual = table.getAllPiles().get(6).size();
        assertEquals(0, actual, "Assert that pile 6 is empty");
        match.nextPlayerCard = table.stringToCardConverter("E");
        move.insertNextCardFromInput(match);
        assertEquals(0, table.getAllPiles().get(6).size(), "Assert that the pile is still empty");
    //Rerun algorithm
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("H12");
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        boolean hasMatch = match.match;
        assertTrue(hasMatch, "Check if match object is true");
        actual = match.fromPile;
        assertEquals(11, actual, "Check if from pile is correct");
        actual = match.toPile;
        assertEquals(6, actual, "Check if ToPile is correct");
    //Check if the queen does not match with the king
    //First reinit the table
        table = new TableIO();
        algorithm = new Algorithm(table);
        move = new Mover(table);
        table.initStartTable("H7,K2,H2,S2,K10,S11,S0");
        match = algorithm.checkForAnyMatch();
        move.moveCard_OrPile(match);
        actual = table.getAllPiles().get(6).size();
        assertEquals(0, actual, "Assert that pile 6 is empty");
        match.nextPlayerCard = table.stringToCardConverter("E");
        move.insertNextCardFromInput(match);
        assertEquals(0, table.getAllPiles().get(6).size(), "Assert that the pile is still empty");
        //Rerun algorithm
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("S12");  //Insert spade of king instead. That should not trigger a match
        move.insertNextCardFromInput(match);
        match = algorithm.checkForAnyMatch();
        hasMatch = match.match;
        assertFalse(hasMatch, "Check if match object is false, as it should be");
    }

    @Test
    void checkFromTablouPile_ToTablouPile() {
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H9,S2,K10,S12,R10");
        Match match = algorithm.checkForAnyMatch();
        assertEquals(2, match.fromPile);
        assertEquals(1, match.toPile);
    }
    @Test
    void checkFromTablouPile_ToFoundation(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H9,S2,K10,S0,R10");
        Match match = algorithm.checkForAnyMatch();
        assertEquals(5, match.fromPile);
        assertEquals(10, match.toPile);
    }
    @Test
    void checkForNoMatchCondition(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,S2,K10,H2,R10");
        Match match = algorithm.checkForAnyMatch();
        assertFalse(match.match);
    }
    @Test
    void checkFromPlayerCard_ToFoundation(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,S2,K10,H2,R10");
        algorithm.checkForAnyMatch();
        table.getPlayerDeck().add(2, new Card(0, 0, 0, true, 11));
        table.setPlayerDeckIndex(2);
        Match match = algorithm.checkForAnyMatch();
        assertEquals(11, match.fromPile);
        assertEquals(7, match.toPile);
    }
    @Test
    void checkFromPlayerDeck_ToTablou(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,S2,K10,H2,R10");
        algorithm.checkForAnyMatch();
        table.getPlayerDeck().add(2, new Card(1,1,9,true, 11));
        table.setPlayerDeckIndex(2);
        Match match = algorithm.checkForAnyMatch();
        assertEquals(1, match.toPile);
    }

    @Test
    void testForKingMatch(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H3,H3,H3,S3,K11,H3,R11");
        table.getAllPiles().get(6).remove(0);
        table.getPlayerDeck().add(2, new Card(1,1,12,true, 11));
        table.setPlayerDeckIndex(2);
        Match match = algorithm.checkForAnyMatch();
        assertEquals(11, match.fromPile);
    }

    @Test
    void checkForComplexMatch(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,H2,K10,R2,R10");
        //algorithm.checkForAnyMatch();
        table.getAllPiles().get(3).add(new Card(3,0,1,true, 3));
        table.getFundamentPiles().get(1).add(new Card(1, 1, 0, true, 8));
        table.getFundamentPiles().get(1).add(new Card(1, 0, 1, true, 8));
        //table.setPlayerDeckIndex(2);
        Match match = algorithm.checkForAnyMatch();
        assertEquals(5, match.toPile);
        assertEquals(3, match.fromPile);
        assertTrue(match.complex);
        assertEquals(4, match.complexIndex);
    }
}