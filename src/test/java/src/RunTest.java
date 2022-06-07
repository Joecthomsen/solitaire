package src;

import org.junit.jupiter.api.Test;
import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RunTest {

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
            else{
                System.out.println("Turn over card in player deck");
                match.setFromPile(11);
                move.moveCard_OrPile(match);
            }
            move.insertNextCardFromInput(match);
            if(randomNumber.getUpperbound() != 0) {
                randomNumber.setUpperbound(randomNumber.getUpperbound() - 1);
            }
        }
    }
}