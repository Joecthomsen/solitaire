package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Mover implements Move {

    Table table;
    //int cardsLeft;
    Mover(Table table){
        this.table = table;
    }

    @Override
    public void turnOverNewCard_PlayerDeck(Card newCard) {

    }

    @Override
    public void moveComplexPile(int fromPile, int fromIndex, int toPile) {

    }

    @Override
    public void moveCard_OrPile(Match match) {
        if(match.complex){
            //First step tablou to tablou
            table.getAllPiles().get(match.toPile).add(table.getAllPiles().get(match.fromPile).get(match.complexIndex));
            table.getAllPiles().get(match.fromPile).remove(match.complexIndex);
            //Second step tablou to foundation
            table.getFundamentPiles().get(match.getComplexFinalFoundationPile()).add(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1));
            table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
            //Check if we need the next input
            if(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).isFaceUp()){
                match.setNoNextInput(true);
            }
        }

    //If we move from player pile to tablou pile
        if (match.fromPile == 11 && match.toPile < 7 && match.match)
        {
            table.getAllPiles().get(match.toPile).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
            table.getAllPiles().get(match.toPile).get(table.getAllPiles().get(match.toPile).size() - 1).setBelongToPile(match.toPile);
            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);

            checkIfNextCardIsKnown(match);
        }

    //If we move from player deck to foundation pile
        else if(match.match && match.fromPile == 11){   //If we move from player pile to foundation pile
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
            table.getFundamentPiles().get(match.toPile - 7).get(table.getFundamentPiles().get(match.toPile - 7).size() - 1).setBelongToPile(match.toPile);
            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);

            checkIfNextCardIsKnown(match);
        }
    //If we want to move from tablou to tablou
        else if(match.match && match.toPile < 7 && match.fromPile < 7) {

            List<Card> cardsToMove = new ArrayList<>();
            //Create list of cards
            for (int i = 0 ; i < table.getAllPiles().get(match.fromPile).size() ; i++){
                if (table.getAllPiles().get(match.fromPile).get(i).isFaceUp()){
                    cardsToMove.add(table.getAllPiles().get(match.fromPile).get(i));
                }
            }
            for (int i = 0 ; i < table.getAllPiles().get(match.fromPile).size() ; i++){
                if (table.getAllPiles().get(match.fromPile).get(i).isFaceUp()){
                    table.getAllPiles().get(match.fromPile).remove(i);
                    i--;
                }
            }
            for (int i = 0 ; i < cardsToMove.size() ; i++){
                cardsToMove.get(i).setBelongToPile(match.toPile);
            }
            table.getAllPiles().get(match.toPile).addAll(cardsToMove);
            if(table.getAllPiles().get(match.fromPile).isEmpty()){
                match.setLastCardInPile(true);
            }
            else if(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).isFaceUp()){
                match.setNoNextInput(true);
            }
        }
        //If match from tablou to fundation
        else
        {
            //Copy card to fundamental pile
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1));
            //Set new pile number for card
            table.getTopCard_fromFundamentStack(match.toPile - 7).setBelongToPile(match.toPile);
            //Delete from old pile
            table.getPile(match.fromPile).remove(table.getPile(match.fromPile).size() - 1);
            if(table.getAllPiles().get(match.fromPile).isEmpty()){
                match.setLastCardInPile(true);
            }
            else if(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() -1 ).isFaceUp()){
                match.setNoNextInput(true);
            }
        }
    }

    private void checkIfNextCardIsKnown(Match match) {
        if (table.getPlayerDeck_FaceUp().size() > 0) {
            if (table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).isFaceUp()) {
                match.setNoNextInput(true);
                System.out.println("Next card is known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).toString());
            }
        }
    }

    @Override
    public void insertNextCardFromInput(Match match) {

        //If there is no match, and we wants to turn three card in the player deck.
        if (match.getFromPile() == 11 && !match.match){
            if(table.getPlayerDeck_FaceDown().size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                    table.getPlayerDeck_FaceDown().remove(0);
                }
                setNewCard(match);
            }
            else if(table.getPlayerDeck_FaceDown().size() < 3 && table.getPlayerDeck_FaceDown().size() != 0){
                table.getPlayerDeck_FaceDown().addAll(table.getPlayerDeck_FaceUp());
                while (table.getPlayerDeck_FaceUp().size() != 0) {
                    table.getPlayerDeck_FaceUp().remove(0);
                }
                //table.getPlayerDeck_FaceUp().clear();
                for (int i = 0 ; i < 2 ; i++){
                    table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                    table.getPlayerDeck_FaceDown().remove(0);
                }
                table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
                table.getPlayerDeck_FaceDown().remove(0);
                setNewCard(match);
            }
            else{
                if (table.getPlayerDeck_FaceDown().size() == 0){
                    table.getPlayerDeck_FaceDown().addAll(table.getPlayerDeck_FaceUp());
                    table.getPlayerDeck_FaceUp().clear();
                    for (int i = 0; i < 2; i++) {
                        table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                        table.getPlayerDeck_FaceDown().remove(0);
                    }
                    if (table.getPlayerDeck_FaceDown().get(0).isFaceUp()) {
                        match.setNoNextInput(true);
                        System.out.println("Card known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                        table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                        table.getPlayerDeck_FaceDown().remove(0);
                    }
                    else {
                        table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
                        table.getPlayerDeck_FaceDown().remove(0);
                    }
                }
            }
        }

    //If the card from the player deck is a match to the tablou piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile < 7){
            setNewCard(match);
        }

    //If the card from the player deck is a match to the foundation piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile >= 7){
            table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
            if(table.getPlayerDeck_FaceUp().size() > 1) {
                table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 2);
            }
            setNewCard(match);
        }
    //From tablou to tablou
        else if(match.match && match.fromPile < 7 && match.toPile < 7){
            table.getAllPiles().get(match.fromPile).add(match.nextPlayerCard);
            if(table.getAllPiles().get(match.fromPile).size() > 1) {
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 2);
            }
            table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).setBelongToPile(match.fromPile);
        }

    //Match to foundation pile from tablou
        else if(match.match && match.fromPile < 7){
            if (!match.lastCardInPile) {
                table.getAllPiles().get(match.fromPile).add(table.getAllPiles().get(match.fromPile).size() - 1, match.nextPlayerCard);
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
                table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).setBelongToPile(match.fromPile);
            }
            if(table.getAllPiles().get(match.fromPile).size() > 1){
                if (table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).isFaceUp()){
                    match.setNoNextInput(true);
                }
            }
        }

    //If the match is from the tablou pile to another tablou pile (i think)
        else if(!table.getAllPiles().get(match.fromPile).isEmpty()) {
            setNewCard(match);
        }
        //If we have a complex move
        else if(match.complex){

        }
        else System.out.println("EMPTY PILE!");
    }

    private void setNewCard(Match match) {
        if(!table.getPlayerDeck_FaceUp().isEmpty()) {
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setFaceUp(true);
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setColor(match.nextPlayerCard.getColor());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setValue(match.nextPlayerCard.getValue());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setType(match.nextPlayerCard.getType());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setBelongToPile(match.fromPile);
        }
    }
}
