package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.io.IOException;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("H7,S11,H10,S0,K9,S12,R10");
        table.printTable();
        for (int i = 0 ; i < 250 ; i++) {
            Match match = algorithm.checkForAnyMatch();
            if (match.match) {
                System.out.println("Move from pile " + match.fromPile + " to pile " + match.toPile);
                move.moveCard_OrPile(match.fromPile, match.toPile);
            }
            //Take next input
            System.out.println("Enter next card");
            String income = scanner.next();
            Card nextCard = table.stringToCardConverter(income);
            move.insertNextCardFromInput(match, nextCard);
            table.printTable();
        }
    }
}
