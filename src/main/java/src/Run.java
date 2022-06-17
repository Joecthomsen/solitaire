package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.Arrays;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) {

        //TODO Hvis sidste kort fra tablou til foundation bliver tager, skal det printes

        Communicator communicator = new Communicator();
        communicator.initStartTable("H13,R8,H12,R5,K10,S4,R1");


        Scanner scanner =  new Scanner(System.in);
        String retMove;

        while(true){
            retMove = communicator.getNextMove();
            System.out.println(retMove);
            if (retMove == null){
                String cardsString = scanner.next();
                communicator.updateTable(cardsString);
            }
            else {
                String cardsString = scanner.next();
                communicator.updateTable(cardsString);
            }
        }

        /*
        Scanner scanner = new Scanner(System.in);
        Table table = new TableIO();
        Algorithm algorithm = new Algorithm(table);
        Move move = new Mover(table);
        Match match;
        table.initStartTable("H3,H4,S3,K4,K8,R5,S5");
        table.printTable();
        boolean printTable = true;
        for (int i = 0 ; i < 250 ; i++) {
            System.out.println("Round: " + i);
            match = algorithm.checkForAnyMatch();
            //No match - Turn card from player pile

            if (match.complex && (match.noNextInput || match.lastCardInPile)){
                System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);
                move.moveCard_OrPile(match);
            }
            else if (match.complex){
                System.out.println("Complex match, first move from pile " + match.fromPile + " at index " + match.complexIndex + " to tablou pile " + match.toPile);
                System.out.println("After that, move the card at tablou pile " + match.fromPile + " to foundation pile " + match.complexFinalFoundationPile);
                System.out.println("Last trun over the facedown card in tablou " + match.fromPile + " and enter value:");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                move.moveCard_OrPile(match);
                table.printTable();
            }
            else if(match.fromPile == 11 && !match.match && !match.noNextInput && !match.lastCardInPile){
                System.out.println("No match on the table, turn three cards from the stock pile over and enter the next card");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                match.nextPlayerCard = card;
                move.moveCard_OrPile(match);
                table.printTable();
            }
            //Match from player pile to tablou - next input
            else if(match.fromPile == 11 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                System.out.println("There is a match from the player pile top tablou pile " + match.toPile);
                System.out.println("Move that and enter the next card in the player pile");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                match.nextPlayerCard = card;
                move.moveCard_OrPile(match);
                table.printTable();
            }
            //Match from stock to foundation - next input
            else if(match.fromPile == 11 && match.toPile >= 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                System.out.println("There is a match from the stock pile to foundation pile " + (match.toPile - 7));
                System.out.println("Move that and enter the next card in the player pile");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                match.nextPlayerCard = card;
                move.moveCard_OrPile(match);
            }
            //Match from stock to foundation - no next input
            else if(match.fromPile == 11 && match.toPile < 7 && match.match && match.noNextInput){
                System.out.println("Take the last card in the face up stock pile, and move it to tablou pile: " + match.toPile);
                move.moveCard_OrPile(match);
                //TODO evt placeres stoppere her, så man får ét move af gangen
            }
            //If we turn three new cards in the player deck and know the next card
            else if(match.fromPile == 11 && !match.match && match.noNextInput){
                System.out.println("Turn over three new cards in stock pile");
                move.moveCard_OrPile(match);
                System.out.printf("The card is already known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
            }
            //Match from tablou to foundation - no next input
            else if(match.fromPile < 7 && match.toPile > 6 && match.match && match.noNextInput && !match.lastCardInPile){
                System.out.println("Move match from tablou pile pile: " + match.fromPile + " to foundation pile: " + (match.toPile - 7));
                System.out.println("That is the last card in the tablou pile number " + match.fromPile);
                move.moveCard_OrPile(match);
            }
            //Match from tablou to foundation - next input
            else if(match.fromPile < 7 && match.toPile > 6 && match.match && !match.noNextInput && !match.lastCardInPile){
                System.out.println("Move match from tabou pile: " + match.fromPile + " to foundation pile: " + (match.toPile - 7));
                System.out.println("Then turn over the face down card in pile: " + match.fromPile + " and enter the input.");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                match.nextPlayerCard = card;
                move.moveCard_OrPile(match);
            }
            //Match from tablou to toblou - next input
            else if(match.fromPile < 7 && match.toPile < 7 && match.match && !match.noNextInput && !match.lastCardInPile){
                System.out.println("Move match from tablou pile: " + match.fromPile + " to tablou pile: " + match.toPile);
                System.out.println("After that, turn over the face down card in tablou pile: " + match.fromPile + " and enter the new cards");
                String input = scanner.next();
                Card card = table.stringToCardConverter(input);
                card.setFaceUp(true);
                match.nextPlayerCard = card;
                move.moveCard_OrPile(match);
                table.printTable();
            }
            //Match from tablou to tablou - no next input
            else if(match.fromPile < 7 && match.toPile < 7 && match.match &&  match.lastCardInPile){
                System.out.println("Move match from tablou pile: " + match.fromPile + " to tablou pile: " + match.toPile);
                System.out.println("The pile is empty after that...");
                move.moveCard_OrPile(match);
            }
            else {
                System.out.printf("Meeeh");
            }

            */






//            //If nothing applies, turn over three cards in stock, if possible
//            else {move.moveCard_OrPile(match); table.printTable();}
//            //table.printTable();
//            System.out.println("Meeeh");
        }
    }

