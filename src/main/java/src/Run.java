package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.Scanner;

public class Run {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        table.initStartTable("K13,S12,S8,S2,K10,R10,K6");
        table.printTable();
        for (int i = 0 ; i < 250 ; i++) {
            Match match = algorithm.checkForAnyMatch();
            if (match.match && !match.complex) {
                System.out.println("Move from pile " + match.fromPile + " to pile " + match.toPile);
                move.moveCard_OrPile(match);
                System.out.println("Enter next card");
                String income = scanner.next();
                match.nextPlayerCard = table.stringToCardConverter(income);
                move.insertNextCardFromInput(match);
                table.printTable();
            }
            else if(match.match){
                System.out.println("Complex match, split pile " + match.fromPile + " at index " + match.complexIndex + " and move to " + match.toPile + " to move that card to the foundation pile");
                move.moveComplexPile(match.fromPile, match.complexIndex, match.toPile);
            }
            else {
                System.out.println("Turn over card in player deck");
                System.out.println("Enter next player card");
                String income = scanner.next();
                match.nextPlayerCard = table.stringToCardConverter(income);
                move.insertNextCardFromInput(match);
                table.printTable();
            }
        }
    }
}
