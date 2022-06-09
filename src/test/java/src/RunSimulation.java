package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

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
}