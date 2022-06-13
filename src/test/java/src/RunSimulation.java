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
                move.insertNextCardFromInput(match);
            }
            else if (match.match && !match.complex) {
                System.out.println("Move from " + match.fromPile + " to " + match.toPile);
                ranNum = randomNumber.getNewNumber();
                move.moveCard_OrPile(match);
            //Now we need to know which cards that was underneath the card.
                match.nextPlayerCard = table.stringToCardConverter(cards.get(ranNum));
                randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
                cards.remove(ranNum);
                move.insertNextCardFromInput(match);
            }
            else {
                System.out.println("Move complex from pile " + match.fromPile + " at index " + match.complexIndex + " to pile " + match.toPile);
                ranNum = randomNumber.getNewNumber();
                move.moveCard_OrPile(match);
                if (!match.lastCardInPile) {
                    match.nextPlayerCard = table.stringToCardConverter(cards.get(ranNum));
                    randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
                    cards.remove(ranNum);
                    move.insertNextCardFromInput(match);
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
            System.out.println("Cards remaining before start table: " + cards.size());
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
                startTable += card.getValue();
                startTable += ",";
                cards.remove(0);
            }
            table.initStartTable(startTable);
            System.out.println("Cards remaining after start table: " + cards.size());


            for (int i = 0 ; i < 250 ; i++) {
                int total = 0;
                for (int j = 0; j < 4; j++) total += table.getFundamentPiles().get(j).size();
                if (total >= 52) {
                    gamesWon++;
                    break;
                }
                totalMovesTaken++;


            /*
            Card cardM = cards.get(0);
            for (int j = 0; j < table.getAllPiles().size(); j++) {
                for (int l = 0; l < table.getAllPiles().get(j).size(); l++) {
                    Card card = table.getAllPiles().get(j).get(l);
                    if (card.getValue() == cardM.getValue() && card.getType() == cardM.getType()){
                        cards.remove(0);
                    }
                }
            }
            for (int j = 0; j < table.getPlayerDeck_FaceUp().size(); j++) {
                Card card = table.getPlayerDeck_FaceUp().get(j);
                if (card.getValue() == cardM.getValue() && card.getType() == cardM.getType()) {
                    cards.remove(0);
                }
            }
            for (int j = 0; j < table.getPlayerDeck_FaceDown().size(); j++) {
                Card card = table.getPlayerDeck_FaceDown().get(j);
                if (card.getValue() == cardM.getValue() && card.getType() == cardM.getType()) {
                    cards.remove(0);
                }
            }
            for (int j = 0; j < table.getFundamentPiles().size(); j++) {
                for (int l = 0; l < table.getFundamentPiles().get(j).size(); l++) {
                    Card card = table.getFundamentPiles().get(j).get(l);
                    if (card.getValue() == cardM.getValue() && card.getType() == cardM.getType()){
                        cards.remove(0);
                    }
                }
            }
            */

                int cardRemoved = 0;
                /*
                for (int j = 0; j < table.getAllPiles().size(); j++) {
                    if (table.getPile(j).size() != 0 && table.getPile(j).get(table.getPile(j).size()-1).getValue() == -1) {
                        table.getPile(j).get(table.getPile(j).size()-1).setValue(cards.get(0).getValue());
                        table.getPile(j).get(table.getPile(j).size()-1).setColor(cards.get(0).getColor());
                        table.getPile(j).get(table.getPile(j).size()-1).setType(cards.get(0).getType());
                        table.getPile(j).get(table.getPile(j).size()-1).setFaceUp(true);
                        cards.remove(0);
                        cardRemoved++;
                    }
                }
                if (table.getPlayerDeck_FaceUp().size() != 0 && table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).getValue() == -1) {
                    table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).setValue(cards.get(0).getValue());
                    table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).setColor(cards.get(0).getColor());
                    table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).setType(cards.get(0).getType());
                    table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size()-1).setFaceUp(true);
                    cards.remove(0);
                    cardRemoved++;
                }

                 */

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

                //assertEquals(cards.size(), unknownCards);

/*
            List<List<Card>> allFaceUp = table.getAllFaceUpCards();
            List<List<Card>> fundamentPiles = table.getFundamentPiles();
            List<Card> deck = table.getPlayerDeck_FaceUp();
            int amountOfFaceUp = 0;
            for (int j = 0; j < allFaceUp.size(); j++) amountOfFaceUp += allFaceUp.get(j).size();
            for (int j = 0; j < deck.size(); j++) {
                if (deck.get(j).getValue() != -1) amountOfFaceUp += 1;
            }
            for (int j = 0; j < fundamentPiles.size(); j++) amountOfFaceUp += fundamentPiles.get(j).size();
 */

            //System.out.println("-----------------------------------------");
            //System.out.println("Amount of known cards: " + amountOfFaceUp);


                //System.out.println("Amount of unknown cards: " + cards.size());


                //System.out.println("-----------------------------------------");
                //assertEquals(52, amountOfFaceUp+cards.size());



                Match match = algorithm.checkForAnyMatch();
                if (match.match && !match.complex) {
                    move.moveCard_OrPile(match);
                    if(!match.lastCardInPile && !match.noNextInput) {
                        match.nextPlayerCard = cards.get(0);
                        cards.remove(0);
                        move.insertNextCardFromInput(match);
                    }
                    else{
                        //System.out.println("Pile empty..");
                    }
                }
                else if(match.match){
                    //System.out.println("Complex match, split pile " + match.fromPile + " at index " + match.complexIndex + " and move to " + match.toPile);
                    //System.out.println("Then move newly freed card in pile " + match.fromPile + "to foundation pile " + (match.complexFinalFoundationPile - 1));
                    move.moveComplexPile(match.fromPile, match.complexIndex, match.toPile);
                    if(!match.lastCardInPile && !match.noNextInput && cards.size() != 0) {
                        match.nextPlayerCard = cards.get(0);
                        cards.remove(0);
                        move.insertNextCardFromInput(match);
                    }
                    else{
                        //System.out.println("Pile empty..");
                    }
                }
                else {
                    if (!match.noNextInput) {
                        match.nextPlayerCard = cards.get(0);
                        cards.remove(0);
                        move.insertNextCardFromInput(match);
                    }
                    else{
                        //System.out.println("MULTIPLE MOVES DETECTED!!!");
                        //System.out.println("*** Turn over card in player deck ***");
                        //System.out.println("Test, card may be: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                        //move.moveCard_OrPile(match);
                    }
                }

                //System.out.println("");
                lastMove = "FromPile: " + Integer.toString(match.fromPile) + ", " + "ToPile: " + Integer.toString(match.toPile);
                int pilesCompleted = 0;
                for (int l = 0; l < 4; l++) {
                    if (table.getFundamentPiles().get(0).size() == 14) pilesCompleted++;
                }
                if (pilesCompleted == 4) {
                    System.out.println("GAME WON!");
                    break;
                }
                if (i == 249) {
                    System.out.println("Game lost :(");
                    break;
                }
            }
        }
        System.out.println("Total games won: " + gamesWon + " out of: " + amountOfGamesToRun + " games.");
        System.out.println("Total moves taken: " + totalMovesTaken);
    }
}