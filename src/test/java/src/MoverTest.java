package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Table;

import static org.junit.jupiter.api.Assertions.*;

public class MoverTest {

    @Test
    void check_insertNextCardFromInput() {
        Table table = new TableIO();
        table.initStartTable("H7,S6,H12,S2,K10,H2,R10");
        Mover mover = new Mover(table);
        Match match = new Match(1, 0, true, false);
        Card card = table.getPile(5).get(0);
        mover.insertNextCardFromInput(match, card);
        assertEquals(card.getValue(), table.getPile(4).get(0).getValue());
        assertEquals(card.getColor(), table.getPile(4).get(0).getColor());
        assertEquals(card.getType(), table.getPile(4).get(0).getType());
        assertEquals(true, table.getPile(4).get(0).isFaceUp());
    }

}
