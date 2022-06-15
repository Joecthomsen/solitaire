package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

public class TestGames {

    @Test
    void testOne(){
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        Match match;
        table.initStartTable("S10,R9,S3,S5,H11,H4,H6");
        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("S7");
        move.moveCard_OrPile(match);

        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("H10");
        move.moveCard_OrPile(match);

        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("K0");
        move.moveCard_OrPile(match);

        match = algorithm.checkForAnyMatch();
        match.nextPlayerCard = table.stringToCardConverter("R9");
        move.moveCard_OrPile(match);
    }
}
