package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {


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

        pileOne.add(new Card(1, 1, 12, true, 0));
        pileTwo.add(new Card(1, 1, 7, true, 1));
        pileThree.add(new Card(1, 1, 2, true, 2));
        pileFour.add(new Card(1, 1, 6, true, 3));

        pileFive.add(new Card(1, 1, 8, true, 4));
        pileFive.add(new Card(0, 0, 7, true, 4));
        pileFive.add(new Card(1, 1, 6, true, 4));
        pileFive.add(new Card(0, 0, 5, true, 4));
        pileFive.add(new Card(1, 1, 4, true, 4));
        pileFive.add(new Card(0, 0, 3, true, 4));
        pileFive.add(new Card(1, 1, 2, true, 4));

        pileSix.add(new Card(1, 1, 11, true, 5));
        pileSeven.add(new Card(1, 1, 3, true, 6));

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
    void checkFromTablouPile_ToTablouPile() {
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H9,S2,K10,S12,R10");
        Match match = algorithm.checkForAnyMatch();
        assertEquals(2, match.fromPile);
        assertEquals(1, match.toPile);
    }

    @Test
    void checkFromTablouPile_ToFoundation() {
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H9,S2,K10,S0,R10");
        Match match = algorithm.checkForAnyMatch();
        assertEquals(5, match.fromPile);
        assertEquals(10, match.toPile);
    }

    @Test
    void checkForNoMatchCondition() {
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        table.initStartTable("H7,S10,H12,S2,K10,H2,R10");
        Match match = algorithm.checkForAnyMatch();
        assertFalse(match.match);
    }

    @Test
    void checkFromPlayerCard_ToFoundation() {
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
    void check_insertNextCardFromInput() {
        Table table = new TableIO();
        table.initStartTable("H7,S6,H12,S2,K10,H2,R10");
        Mover mover = new Mover(table);
        Match match = new Match(1, 0, true, false);
        Card card = table.getPile(1).get(0);
        mover.insertNextCardFromInput(match, card);
        assertEquals(card.getValue(), table.getPile(1).get(0).getValue());
        assertEquals(card.getColor(), table.getPile(1).get(0).getColor());
        assertEquals(card.getType(), table.getPile(1).get(0).getType());
    }

    @Test
    void check_getAllFaceUpCards_fromAPile_move() {
        Table table = new TableIO();
        table.initStartTable("H7,S6,H12,S2,K10,H2,R10");
        Mover mover = new Mover(table);
        Match match = new Match(1, 0, true, false);
        Card card = table.getBottomFaceUpCard_FromPile(1);
        mover.insertNextCardFromInput(match, card);
        assertEquals(2, table.getAllFaceUpCards_fromAPile(0).size());
        assertEquals(7, table.getAllFaceUpCards_fromAPile(0).get(1).getValue());
        assertEquals(6, table.getAllFaceUpCards_fromAPile(0).get(0).getValue());
    }

    @Test
    void check_getBottomFaceUpCard_FromPile() {

    }
}

