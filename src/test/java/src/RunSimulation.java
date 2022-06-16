package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunSimulation {

    @Test
    void testManyGames() {
        String lastMove = "";
        int amountOfGamesToRun = 1000;
        int totalMovesTaken = 0;
        int currentMovesTaken = 0;
        int gamesWon = 0;
        boolean printTable = false;

        for (int k = 0; k < amountOfGamesToRun; k++) {
            Table table = new TableIO();
            Algorithm algorithm = new Algorithm(table);
            Move move = new Mover(table);
            List<Card> cards = new ArrayList<>();
            for (int i = 0 ; i < 4 ;  i++){
                for (int j = 0 ; j < 13 ; j++){
                    Card newCard = new Card();
                    newCard.setValue(j);
                    switch (i)
                    {
                        case 0 :
                            newCard.setColor(0);
                            newCard.setType(0);
                            break;
                        case 1 :
                            newCard.setColor(1);
                            newCard.setType(1);
                            break;
                        case 2 :
                            newCard.setColor(1);
                            newCard.setType(2);
                            break;
                        case 3 :
                            newCard.setColor(0);
                            newCard.setType(3);
                            break;
                    }
                    cards.add(newCard);
                }
            }
            Collections.shuffle(cards);
            String startTable = "";
            for (int i = 0; i < 7; i++) {
                Card card = cards.get(0);
                int type = card.getType();
                switch (type) {
                    case 0 : startTable += "K";
                        break;
                    case 1 : startTable += "H";
                        break;
                    case 2 : startTable += "R";
                        break;
                    case 3 : startTable += "S";
                        break;
                }
                if (card.getValue() != 0) startTable += card.getValue()+1;
                else startTable += card.getValue();
                if (i != 6) startTable += ",";
                cards.remove(0);
            }
            table.initStartTable(startTable);



            Match match;
            currentMovesTaken = 0;
            for (int i = 0 ; i < 999 ; i++) {

                if(printTable) {
                    table.printTable();
                }
                if(printTable) {
                    System.out.println("**** Round " + (i + 1) + " ****");
                }
                int total = 0;
                for (int j = 0; j < 4; j++) total += table.getFundamentPiles().get(j).size();
                if (total >= 52) {
                    gamesWon++;
                    break;
                }
                currentMovesTaken++;

                for (int j = 0; j < table.getAllPiles().size(); j++) {
                    if (table.getAllPiles().get(j).size() != 0 && !table.getAllPiles().get(j).get(table.getAllPiles().get(j).size() - 1).isFaceUp() && table.getAllPiles().get(j).get(table.getAllPiles().get(j).size() - 1).getValue() != -1) {
                        table.getAllPiles().get(j).get(table.getAllPiles().get(j).size() - 1).setFaceUp(true);
                    }
                }
                if (table.getPlayerDeck_FaceUp().size() != 0 && table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).getValue() != -1 && !table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).isFaceUp()) table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).setFaceUp(true);

                int unknownCards = 0;
                for (int j = 0; j < 7; j++) {
                    for (int l = 0; l < table.getPile(j).size(); l++) {
                        if (!table.getPile(j).get(l).isFaceUp()) unknownCards++;
                    }
                }
                for (int j = 0; j < table.getPlayerDeck_FaceDown().size(); j++) {
                    if (!table.getPlayerDeck_FaceDown().get(j).isFaceUp()) unknownCards++;
                }
                for (int j = 0; j < table.getPlayerDeck_FaceUp().size(); j++) {
                    if (!table.getPlayerDeck_FaceUp().get(j).isFaceUp()) unknownCards++;
                }

                if (unknownCards != cards.size()) {
                    System.out.print("");
                }

                if (cards.size() == 0) {
                    System.out.printf("");
                }

                //assertEquals(cards.size(), unknownCards);
                int totalCardsInTablou = table.getAllPiles().get(0).size() + table.getAllPiles().get(1).size() + table.getAllPiles().get(2).size() + table.getAllPiles().get(3).size() + table.getAllPiles().get(4).size() + table.getAllPiles().get(5).size() + table.getAllPiles().get(6).size();
                int totalCardsInFoundation = table.getFundamentPiles().get(0).size() + table.getFundamentPiles().get(1).size() + table.getFundamentPiles().get(2).size() + table.getFundamentPiles().get(3).size() - 4;
                int totalCardsInStock = table.getPlayerDeck_FaceUp().size() + table.getPlayerDeck_FaceDown().size();

                int totalCardsInGame = totalCardsInFoundation + totalCardsInStock + totalCardsInTablou;

                if (totalCardsInGame != 52){
                    System.out.printf("");
                }
                if(table.getFundamentPiles().get(0).get(table.getFundamentPiles().get(0).size() - 1).getBelongToPile() < 7 ||
                        table.getFundamentPiles().get(1).get(table.getFundamentPiles().get(1).size() - 1).getBelongToPile() < 7 ||
                        table.getFundamentPiles().get(2).get(table.getFundamentPiles().get(2).size() - 1).getBelongToPile() < 7 ||
                        table.getFundamentPiles().get(3).get(table.getFundamentPiles().get(3).size() - 1).getBelongToPile() < 7){
                    System.out.printf("");
                }

                match = algorithm.checkForAnyMatch();
                if(match.toPile == 11){
                    System.out.println("");
                }
                if (cards.size() == 0){
                    System.out.printf("");
                }
                if (!match.match){
                    System.out.printf("");
                }
                System.out.println("");

                int cardsBefore = cards.size();

                if (match.complex && (match.noNextInput || match.lastCardInPile)){
                    if(printTable){
                        System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                        System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);}
                    move.moveCard_OrPile(match);
                }
                else if (match.complex){
                    if(printTable) {
                        System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                        System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);
                        System.out.println("Last trun over the facedown card in tablou " + match.fromPile + " and enter value:");
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                    if(printTable) {
                        table.printTable();
                    }
                }
                //No match - Turn card from player pile - next input
                else if(match.fromPile == 11 && !match.match && !match.noNextInput && !match.lastCardInPile){
                    if(printTable) {
                        System.out.println("Turn over three new cards in the stock pile");
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //No match - Turn card from player pile - no next input
                else if(match.fromPile == 11 && !match.match && match.noNextInput && !match.lastCardInPile) {
                    if (printTable){
                        System.out.println("Turn over three new cards in the stock pile");
                    }
                    move.moveCard_OrPile(match);
                    //System.out.printf("The next card you turn over is known and is: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                }
                //Match from player pile to tablou - next input
                else if(match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - next input
                else if(match.fromPile == 11 && match.toPile >= 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - no next input
                else if(match.fromPile == 11 && match.toPile > 6 && match.match && match.noNextInput){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from stock to tablou - next input
                else if(match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput){
                    if (printTable) {
                        System.out.println("Move from stock to tablou pile: " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from stock to tablou - no next input
                else if(match.fromPile == 11 && match.toPile < 7 && match.match){
                    if(printTable) {
                        System.out.println("Move from stock to tablou pile: " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                    //System.out.println("The next card in stock pile is known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                }
                //Match from tablou to foundation - no next input
                else if(match.fromPile < 7 && match.toPile > 6 && match.match && match.noNextInput){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to foundation - next input
                else if(match.fromPile < 7 && match.toPile > 6 && match.match && !match.noNextInput){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to toblou - next input
                else if(match.fromPile < 7 && match.toPile < 7 && match.match && !match.noNextInput){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to tablou - no next input
                else if(match.fromPile < 7 && match.toPile < 7 && match.match && match.noNextInput){
                    if(printTable) {
                        System.out.println("move from " + match.fromPile + " to " + match.toPile);
                    }
                    move.moveCard_OrPile(match);
                }
                else {
                    System.out.printf("Meeeh");
                }

                lastMove = "FromPile: " + Integer.toString(match.fromPile) + ", " + "ToPile: " + Integer.toString(match.toPile);
                int pilesCompleted = 0;
                for (int l = 0; l < 4; l++) {
                    if (table.getFundamentPiles().get(0).size() == 14) pilesCompleted++;
                }
                if (pilesCompleted == 4) {
                    System.out.println("GAME WON! " + currentMovesTaken + " moves taken for this win.");
                    break;
                }
                if (i == 999) {
                    System.out.println("Game lost: " + table.getFundamentPiles().get(0).size() + ", " + table.getFundamentPiles().get(1).size() + ", " + table.getFundamentPiles().get(2).size() + ", " + table.getFundamentPiles().get(3).size() + ".");
                    break;
                }
                System.out.println("CARDS TOTAL: " +totalCardsInGame);
                int cardsAfter = cardsBefore - cards.size();
                if(cardsAfter > 1){
                    System.out.printf("");
                }
                if (i > 250){
                    System.out.printf("");
                }
                if(table.getFundamentPiles().get(0).get(table.getFundamentPiles().get(0).size() - 1).getType() != 0 ||
                        table.getFundamentPiles().get(1).get(table.getFundamentPiles().get(1).size() - 1).getType() != 1 ||
                        table.getFundamentPiles().get(2).get(table.getFundamentPiles().get(2).size() - 1).getType() != 2 ||
                        table.getFundamentPiles().get(3).get(table.getFundamentPiles().get(3).size() - 1).getType() != 3){
                    System.out.println(match);
                    System.out.printf("");
                }
            }
            totalMovesTaken += currentMovesTaken;
            if(table.getFundamentPiles().get(0).size() > 13 && table.getFundamentPiles().get(1).size() > 13 && table.getFundamentPiles().get(2).size() > 13 && table.getFundamentPiles().get(3).size() > 13){
                System.out.println("");
            }
        }
        System.out.println("Total games won: " + gamesWon + " out of: " + amountOfGamesToRun + " games.");
        System.out.println("Total moves taken: " + totalMovesTaken);
    }
}