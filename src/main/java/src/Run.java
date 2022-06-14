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
        table.initStartTable("K5,H11,H7,H4,S5,H13,H8");
        table.printTable();
        for (int i = 0 ; i < 250 ; i++) {
            match = algorithm.checkForAnyMatch();
            if (match.match && !match.complex) {
                System.out.println("Move from pile " + match.fromPile + " to pile " + match.toPile);
                move.moveCard_OrPile(match);
                if(!match.lastCardInPile && !match.noNextInput) {
                    System.out.println("Enter next card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else{
                    System.out.println("Pile is empty...");
                }
            }
            else if(match.match){
                System.out.println("Complex match, split pile " + match.fromPile + " at index " + match.complexIndex + " and move to " + match.toPile);
                System.out.println("Then move newly freed card in pile " + match.fromPile + "to foundation pile " + (match.complexFinalFoundationPile - 1));
                //move.moveComplexPile(match.fromPile, match.complexIndex, match.toPile);
                move.moveCard_OrPile(match);
                if(!match.lastCardInPile && !match.noNextInput) {
                    System.out.println("Enter next card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else{
                    System.out.println("No input needed...");
                }
            }
            else {
                if (!match.noNextInput && !match.lastCardInPile && !move.getIsStockPileIsEmpty()) {
                    System.out.println("Turn over card in player deck");
                    System.out.println("Enter next player card");
                    String income = scanner.next();
                    match.nextPlayerCard = table.stringToCardConverter(income);
                    move.insertNextCardFromInput(match);
                    table.printTable();
                }
                else if(!move.getIsStockPileIsEmpty()){
                    System.out.println("MULTIPLE MOVES DETECTED!!!");
                    System.out.println("*** Turn over card in player deck and find: "  + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                    //System.out.println("Test, card may be: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                    //move.moveCard_OrPile(match);
                }
                else {
                    System.out.println("Stock pile is empty!");
                }
            }
        }
    }
}
