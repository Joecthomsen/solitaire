package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
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

        pileOne.add(new Card(1,1,12,true,0));
        pileTwo.add(new Card(1,1,7,true,1));
        pileThree.add(new Card(1,1,2,true,2));
        pileFour.add(new Card(1,1,6,true,3));
        pileFive.add(new Card(1,1,8,true,4));
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
    void checkForAnyMatch() {

    }
}