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
        Match match;
        table.initStartTable("R3,K4,K6,R9,H3,H4,K2");
        table.printTable();
        for (int i = 0 ; i < 250 ; i++) {
            match = algorithm.checkForAnyMatch();
            if (match.match && !match.complex) {
                System.out.println("Move from pile " + match.fromPile + " to pile " + match.toPile);
                move.moveCard_OrPile(match);
                if(!match.lastCardInPile || !match.noNextInput) {
                    System.out.println("Enter next card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else{
                    System.out.println("Pile empty..");
                }
            }
            else if(match.match){
                System.out.println("Complex match, split pile " + match.fromPile + " at index " + match.complexIndex + " and move to " + match.toPile + " to move that card to the foundation pile");
                move.moveComplexPile(match.fromPile, match.complexIndex, match.toPile);
                if(!match.lastCardInPile || !match.noNextInput) {
                    System.out.println("Enter next card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else{
                    System.out.println("Pile empty..");
                }
            }
            else {
                if (!match.noNextInput) {
                    System.out.println("Turn over card in player deck");
                    System.out.println("Enter next player card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else{
                    System.out.println("MULTIPLE MOVES DETECTED!!!");
                    System.out.println("*** Turn over card in player deck ***");
                    move.insertNextCardFromInput(match);
                }
            }
        }
    }
}
