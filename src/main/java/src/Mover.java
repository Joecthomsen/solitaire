package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

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

    private boolean stockPileIsEmpty = false;

    @Override
    public void moveCard_OrPile(Match match) {

        //If there is a complex match
        if (match.complex) {
            //First step tablou to tablou
            List<Card> cardsToMove = new ArrayList<>();

            for (int i = match.complexIndex; i < table.getAllPiles().get(match.fromPile).size(); i++) {
                cardsToMove.add(table.getAllPiles().get(match.fromPile).get(i));
                table.getAllPiles().get(match.fromPile).remove(match.complexIndex);
            }
            table.getFundamentPiles().get(match.getComplexFinalFoundationPile()).add(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1));
            table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
            //Check if we need the next input
            if (table.getAllPiles().get(match.fromPile).size() > 0) {
                if (table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1).isFaceUp()) {
                    match.setNoNextInput(true);
                }
            }
        }
        //If there is no match, and we need 3 new cards
        else if (!match.match && !match.noNextInput) {
            if (table.getPlayerDeck_FaceDown().size() > 2) {
                for (int i = 0; i < 2; i++) {
                    table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                    table.getPlayerDeck_FaceDown().remove(0);
                }
                table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
                table.getPlayerDeck_FaceDown().remove(0);
            } else if (table.getPlayerDeck_FaceDown().size() == 2) {
                table.getPlayerDeck_FaceDown().addAll(table.getPlayerDeck_FaceUp());
                table.getPlayerDeck_FaceUp().clear();
                for (int i = 0; i < 2; i++) {
                    table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                    table.getPlayerDeck_FaceDown().remove(0);
                }
                table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
                table.getPlayerDeck_FaceDown().remove(0);
            }
        }
        //If there is a match and the next input is attached
        else if (match.match && !match.noNextInput) {
            //If match from stock pile
            if (match.fromPile == 11) {
                if (match.toPile < 7) {
                    table.getAllPiles().get(match.toPile).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                    table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
                    if (!table.getPlayerDeck_FaceUp().isEmpty()) {
                        table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
                        match.nextPlayerCard.setBelongToPile(match.toPile);
                        table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);
                    }
                }
                else {
                    table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                    table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
                    table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
                    match.nextPlayerCard.setBelongToPile(match.toPile);
                    table.getPlayerDeck_FaceUp().add(match.nextPlayerCard);

                }
            }
            //If there is a match from the tablou to tablou
            else if(match.fromPile < 7 && match.toPile < 7) {
                //First move the cards from pile to pile
                List<Card> cardsToMove = new ArrayList<>();
                for (int i = 0 ; i < table.getAllPiles().get(match.fromPile).size() ; i++){
                    if(table.getAllPiles().get(match.fromPile).get(i).isFaceUp()) {
                        cardsToMove.add(table.getAllPiles().get(match.fromPile).get(i));
                        table.getAllPiles().get(match.fromPile).remove(i);
                        i--;
                    }
                }
                for (int i = 0 ; i < cardsToMove.size() ; i++){
                    cardsToMove.get(i).setBelongToPile(match.toPile);
                }
                table.getAllPiles().get(match.toPile).addAll(cardsToMove);
                //Flip the next cards
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
                table.getAllPiles().get(match.fromPile).add(match.nextPlayerCard);
                table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() -1).setBelongToPile(match.fromPile);
            }
            //If there is a match from tablou to foundation
            else if(match.fromPile < 7){
                //Copy from tablou to foundation
                table.getFundamentPiles().get(match.toPile - 7).add(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1));
                //Remove the two next cards (we know that we need an input)
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
                match.nextPlayerCard.setBelongToPile(match.fromPile);
                table.getAllPiles().get(match.fromPile).add(match.nextPlayerCard);
            }
        }
        //If there is a match and the next input is NOT needed
        else if (match.match) {
            if (match.fromPile == 11 && match.toPile < 7) {
                if (table.getPlayerDeck_FaceUp().size() == 1) {
                    table.getAllPiles().get(match.toPile).add(table.getPlayerDeck_FaceUp().get(0));
                    table.getPlayerDeck_FaceUp().clear();
                }
            }
            //Match from tablou to foundation
            else if(match.fromPile < 7 && match.toPile > 6){
                table.getFundamentPiles().get(match.toPile - 7).add(table.getAllPiles().get(match.fromPile).get(table.getAllPiles().get(match.fromPile).size() - 1));
                table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 1);
            }
            //Match from tablou to tablou
            else if(match.fromPile < 7 && match.toPile < 7){
                table.getAllPiles().get(match.toPile).addAll(table.getAllPiles().get(match.fromPile));
                table.getAllPiles().get(match.fromPile).clear();
            }
        }
    }
    private void checkIfNextCard_InStockPile_IsKnown(Match match) {
        if (table.getPlayerDeck_FaceUp().size() > 0) {
            if (table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).isFaceUp()) {
                match.setNoNextInput(true);
                //System.out.println("Next card is known: " + table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).toString());
            }
            else if(table.getPlayerDeck_FaceDown().size() > 2){
                if (table.getPlayerDeck_FaceDown().get(2).isFaceUp()){
                    match.setNoNextInput(true);
                }
            }
        }
    }


    @Override
    public boolean getIsStockPileIsEmpty() {
        return stockPileIsEmpty;
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
