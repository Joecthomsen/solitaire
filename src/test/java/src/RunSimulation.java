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
    void runOneFullGame(){

        List<String> cards = new ArrayList<>();
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        RandomNumber randomNumber = new RandomNumber(52);

        for (int i = 0 ; i < 4 ;  i++){
            for (int j = 0 ; j < 13 ; j++){
                switch (i)
                {
                    case 0 : cards.add("K" + j);
                    break;
                    case 1 : cards.add("H" + j);
                    break;
                    case 2 : cards.add("R" + j);
                    break;
                    case 3 : cards.add("S" + j);
                    break;
                }
            }
        }

        int initString = randomNumber.getNewNumber();
        String initTable = cards.get(initString);
        cards.remove(initString);
        randomNumber.setUpperbound(51);

        for (int i = 0 ; i < 6 ; i++){
            int ranNum = randomNumber.getNewNumber();
            initTable = initTable.concat("," + cards.get(ranNum));
            cards.remove(ranNum);
            randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
        }
        System.out.println(initTable);
        table.initStartTable(initTable);
        table.printTable();
        Match match; // = algorithm.checkForAnyMatch();
        int ranNum;//  = randomNumber.getNewNumber();
    //Runnable
        for (int i = 0 ; i < 250 ; i++) {

            match = algorithm.checkForAnyMatch();

            if (!match.match & !match.complex) {
                ranNum = randomNumber.getNewNumber();
                System.out.println("Turn over card in player pile");
                match.nextPlayerCard = table.stringToCardConverter(cards.get(ranNum));
                randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
                cards.remove(ranNum);
                //move.insertNextCardFromInput(match);
            }
            else if (match.match && !match.complex) {
                System.out.println("Move from " + match.fromPile + " to " + match.toPile);
                ranNum = randomNumber.getNewNumber();
                move.moveCard_OrPile(match);
            //Now we need to know which cards that was underneath the card.
                match.nextPlayerCard = table.stringToCardConverter(cards.get(ranNum));
                randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
                cards.remove(ranNum);
                //move.insertNextCardFromInput(match);
            }
            else {
                System.out.println("Move complex from pile " + match.fromPile + " at index " + match.complexIndex + " to pile " + match.toPile);
                ranNum = randomNumber.getNewNumber();
                move.moveCard_OrPile(match);
                if (!match.lastCardInPile) {
                    match.nextPlayerCard = table.stringToCardConverter(cards.get(ranNum));
                    randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
                    cards.remove(ranNum);
                    //move.insertNextCardFromInput(match);
                }
            }
            table.printTable();
        }
    }


    @Test
    void testManyGames() {
        String lastMove = "";
        int amountOfGamesToRun = 1000;
        int totalMovesTaken = 0;
        int currentMovesTaken = 0;
        int gamesWon = 0;

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


            currentMovesTaken = 0;
            for (int i = 0 ; i < 1000 ; i++) {
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

                //assertEquals(cards.size(), unknownCards);

                Match match = algorithm.checkForAnyMatch();
                //No match - Turn card from player pile
                if(match.fromPile == 11 && !match.match && !match.noNextInput && !match.lastCardInPile){
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from player pile to tablou - next input
                else if(match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - next input
                else if(match.fromPile == 11 && match.toPile >= 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from stock to foundation - no next input
                else if(match.fromPile == 11 && match.toPile < 7 && match.match && match.noNextInput){
                    move.moveCard_OrPile(match);
                    //TODO evt placeres stoppere her, så man får ét move af gangen
                }
                //Match from tablou to foundation - no next input
                else if(match.fromPile < 7 && match.toPile > 6 && match.match && match.noNextInput && !match.lastCardInPile){
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to foundation - next input
                else if(match.fromPile < 7 && match.toPile > 6 && match.match && !match.noNextInput && !match.lastCardInPile){
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to toblou - next input
                else if(match.fromPile < 7 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                    Card card = cards.get(0);
                    cards.remove(0);
                    card.setFaceUp(true);
                    match.nextPlayerCard = card;
                    move.moveCard_OrPile(match);
                }
                //Match from tablou to tablou - no next input
                else if(match.fromPile < 7 && match.toPile < 7 && match.match && match.noNextInput && !match.lastCardInPile){

                    move.moveCard_OrPile(match);
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