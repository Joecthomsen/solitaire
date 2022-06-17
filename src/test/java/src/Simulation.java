package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

public class Simulation {
    int numberOfGames;
    int maximumNumberOfHandsInEachGame;

    int handsLost = 0;
    int handsWon = 0;
    boolean printTable;

    public Simulation(int numberOfGames, int maximumNumberOfHandsInEachGame, boolean printTable) {
        this.numberOfGames = numberOfGames;
        this.maximumNumberOfHandsInEachGame = maximumNumberOfHandsInEachGame;
        this.printTable = printTable;
    }
    public TestResult runSimulation() {
        for (int i = 0; i < numberOfGames; i++) {
            Table table = new TableIO();
            Algorithm algorithm = new Algorithm(table);
            Move move = new Mover(table);
            Match match;
            RandomCards randomCards = new RandomCards();
            table.initStartTable(randomCards.getStartTableString());

            for (int j = 0; j < maximumNumberOfHandsInEachGame; j++) {
                match = algorithm.checkForAnyMatch();
                if (match.complex && (match.noNextInput || match.lastCardInPile)) {
                    if (printTable) {
                        System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                        System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);
                    }
                    move.moveCard_OrPile(match);
                } else if (match.complex && !match.noNextInput) {
                    if (printTable) {
                        System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                        System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);
                        System.out.println("Last trun over the facedown card in tablou " + match.fromPile + " and enter value:");
                    }

                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                    if (printTable) {
                        table.printTable();
                    }
                }
                //No match - Turn card from player pile - next input
                else if (match.fromPile == 11 && !match.match && !match.noNextInput && !match.lastCardInPile) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("Turn over three new cards in the stock pile");
                    }
                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                }
                //No match - Turn card from player pile - no next input
                else if (match.fromPile == 11 && !match.match && match.noNextInput && !match.lastCardInPile) {
                    if (printTable) {
                        System.out.println("Turn over three new cards in the stock pile");
                    }
                    move.moveCard_OrPile(match);
                    //System.out.printf("The next card you turn over is known and is: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                }
                //Match from player pile to tablou - next input
                else if (match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - next input
                else if (match.fromPile == 11 && match.toPile >= 7 && match.match && !match.noNextInput && !match.lastCardInPile) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - no next input
                else if (match.fromPile == 11 && match.toPile > 6 && match.match && match.noNextInput) {
                    if (printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from stock to tablou - next input
                else if (match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("Move from stock to tablou pile: " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from stock to tablou - no next input
                else if (match.fromPile == 11 && match.toPile < 7 && match.match) {
                    if (printTable) {
                        System.out.println("Move from stock to tablou pile: " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                    //System.out.println("The next card in stock pile is known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                }
                //Match from tablou to foundation - no next input
                else if (match.fromPile < 7 && match.toPile > 6 && match.match && match.noNextInput) {
                    if (printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to foundation - next input
                else if (match.fromPile < 7 && match.toPile > 6 && match.match && !match.noNextInput) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to toblou - next input
                else if (match.fromPile < 7 && match.toPile < 7 && match.match && !match.noNextInput) {
                    if (printTable) {
                        table.printTable();
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    match.nextPlayerCard = table.stringToCardConverter(randomCards.getNextCard());
                    match.nextPlayerCard.setFaceUp(true);
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to tablou - no next input
                else if (match.fromPile < 7 && match.toPile < 7 && match.match && match.noNextInput) {
                    if (printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                } else {
                    System.out.printf("Meeeh");
                }

                if (table.getFundamentPiles().get(0).size() == 14 && table.getFundamentPiles().get(1).size() == 14 &&
                        table.getFundamentPiles().get(2).size() == 14 && table.getFundamentPiles().get(3).size() == 14) {
                    handsWon++;
                    break;
                }
                if (j == maximumNumberOfHandsInEachGame - 1){
                    handsLost++;
                }
            }
        }
        return new TestResult(handsWon, handsLost);
    }
}


