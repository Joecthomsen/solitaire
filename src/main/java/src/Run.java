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
        table.initStartTable("H1,H12,K2,R8,H7,H0,K11");
        table.printTable();
        for (int i = 0 ; i < 250 ; i++) {
            Match match = algorithm.checkForAnyMatch();
            if (match.match && !match.complex) {
                System.out.println("Move from pile " + match.fromPile + " to pile " + match.toPile);
                move.moveCard_OrPile(match);
                if(table.getAllPiles().get(match.fromPile).isEmpty()){continue;}
            }
            else if(match.match){
                System.out.println("Complex match, split pile " + match.fromPile + " at index " + match.complexIndex + " and move to " + match.toPile + " to move that card to the foundation pile");
                move.moveComplexPile(match.fromPile, match.complexIndex, match.toPile);
            }
            else if(!match.match){
                System.out.println("Turn over card in player deck");
                match.setFromPile(11);
                move.moveCard_OrPile(match);
            }
            //Take next input
            System.out.println("Enter next card");
            String income = scanner.next();
            Card nextCard = table.stringToCardConverter(income);
            move.insertNextCardFromInput(match);
            table.printTable();
            System.out.println("Test");
        }
    }
}
