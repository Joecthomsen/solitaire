package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    void check_initStartTable() {
        Table table = new TableIO();
        String list[] = {"H7", "S6", "H12", "S2", "K10", "H2", "R10"};
        String toTest = "";
        for (int i = 0; i < list.length; i++) {
            toTest += list[i];
            if (i != list.length-1) toTest += ",";
        }
        table.initStartTable(toTest);
        for (int i = 0; i < list.length; i++) {
            char type = list[i].charAt(0);
            char value = list[i].charAt(1);
            if (list[i].length() == 3) value += list[i].charAt(2);
            int valueConverted = Character.getNumericValue(value);

            Card cardFetched = table.getAllFaceUpCards_fromAPile(i).get(0);

            assertEquals(valueConverted, cardFetched.getValue());


            switch (type) {
                case 'K':
                    assertEquals(0, cardFetched.getType());
                    assertEquals(0, cardFetched.getColor());
                    break;
                case 'H':
                    assertEquals(1, cardFetched.getType());
                    assertEquals(1, cardFetched.getColor());
                    break;
                case 'R':
                    assertEquals(2, cardFetched.getType());
                    assertEquals(1, cardFetched.getColor());
                    break;
                case 'S':
                    assertEquals(3, cardFetched.getType());
                    assertEquals(0, cardFetched.getColor());
                    break;
                case 'E':
                default:
                    assertEquals(-1, cardFetched.getType());
                    assertEquals(-1, cardFetched.getColor());
                    break;
            }

        }
    }

    @Test
    void check_stringToCardConverter() {
        /*
                Known issues:
                Test and function will not work if input is incorrect (eg. KK!3 or H3K1)
         */

        Table table = new TableIO();
        String toTest[] = {"K10", "H5", "S1", "K7", "R3", "E10"};
        for (int i = 0; i < toTest.length; i++) {
            Card converted = table.stringToCardConverter(toTest[i]);
            char type = toTest[i].charAt(0);
            char value = toTest[i].charAt(1);
            if (toTest[i].length() == 3) value += toTest[i].charAt(2);
            int valueConverted = Character.getNumericValue(value);
            assertEquals(valueConverted, converted.getValue());


            switch (type) {
                case 'K':
                    assertEquals(0, converted.getType());
                    assertEquals(0, converted.getColor());
                    break;
                case 'H':
                    assertEquals(1, converted.getType());
                    assertEquals(1, converted.getColor());
                    break;
                case 'R':
                    assertEquals(2, converted.getType());
                    assertEquals(1, converted.getColor());
                    break;
                case 'S':
                    assertEquals(3, converted.getType());
                    assertEquals(0, converted.getColor());
                    break;
                case 'E':
                default:
                    assertEquals(-1, converted.getType());
                    assertEquals(-1, converted.getColor());
                    break;
            }
        }
    }


    @Test
    void check_getAllFaceUpCards_fromAPile_initial() {
        Table table = new TableIO();
        String list[] = {"H7", "S6", "H12", "S2", "K10", "H2", "R10"};
        String toTest = "";
        for (int i = 0; i < list.length; i++) {
            toTest += list[i];
            if (i != list.length-1) toTest += ",";
        }
        table.initStartTable(toTest);
        int counter = 7;
        for (int i = 0; i < 7; i++) {
            assertEquals(1, table.getAllFaceUpCards_fromAPile(i).size());
            assertEquals(counter,table.getPile(i).size());
            assertTrue(table.getAllFaceUpCards_fromAPile(i).get(0).isFaceUp());
            counter--;
        }
    }

    @Test
    void check_getAllFaceUpCards() {
        Table table = new TableIO();
        String list[] = {"H7", "S6", "H12", "S2", "K10", "H2", "R10"};
        String toTest = "";
        for (int i = 0; i < list.length; i++) {
            toTest += list[i];
            if (i != list.length-1) toTest += ",";
        }
        table.initStartTable(toTest);

        List<List<Card>> fetched = table.getAllFaceUpCards();

        assertEquals(7, fetched.size());



        for (int i = 0; i < list.length; i++) {
            char type = list[i].charAt(0);
            char value = list[i].charAt(1);
            if (list[i].length() == 3) value += list[i].charAt(2);
            int valueConverted = Character.getNumericValue(value);

            Card cardFetched = table.getAllFaceUpCards_fromAPile(i).get(0);
            assertEquals(valueConverted, cardFetched.getValue());

            switch (type) {
                case 'K':
                    assertEquals(0, cardFetched.getType());
                    assertEquals(0, cardFetched.getColor());
                    break;
                case 'H':
                    assertEquals(1, cardFetched.getType());
                    assertEquals(1, cardFetched.getColor());
                    break;
                case 'R':
                    assertEquals(2, cardFetched.getType());
                    assertEquals(1, cardFetched.getColor());
                    break;
                case 'S':
                    assertEquals(3, cardFetched.getType());
                    assertEquals(0, cardFetched.getColor());
                    break;
                case 'E':
                default:
                    assertEquals(-1, cardFetched.getType());
                    assertEquals(-1, cardFetched.getColor());
                    break;
            }

        }

    }

    @Test
    void check_getBottomFaceUpCard_FromPile() {
        Table table = new TableIO();
        table.initStartTable("H7,S6,H12,S2,K10,H2,R10");
        table.setPile();
        Card card = table.getBottomFaceUpCard_FromPile(0);
        assertEquals(7, card.getValue());
        assertEquals(1, card.getType());
    }


}
