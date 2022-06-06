package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
import java.util.List;

public class Mover implements Move {

    Table table;
    int cardsLeft;
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
        if (match.fromPile == 11 && match.toPile < 7)
        {
            table.getAllPiles().get(match.toPile).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());
        }
        else if(match.fromPile == 11)
        {
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());
        }
        else if(match.toPile < 7) {   //If we want to move the card to a bottom pile
            List<Card> cardsToMove = new ArrayList<>(table.getAllFaceUpCards_fromAPile(match.fromPile));
            //List<Card> cardsToMove = new ArrayList<>(table.getFundamentPiles().get(fromPile - 1));
            for (int i = 0; i < cardsToMove.size(); i++) {
                cardsToMove.get(i).setBelongToPile(match.toPile);
            }
            table.getPile(match.toPile).addAll(cardsToMove);
            //Delete old cards
            table.getPile(match.fromPile).removeAll(table.getAllFaceUpCards_fromAPile(match.fromPile));
            //Set to pile
            table.getPile(match.toPile).get(table.getPile(match.toPile).size() - 1).setBelongToPile(match.toPile);
            //Turn over new card
            //turnOverNextCard_inAPile(newCard, fromPile);
        }
        else
        {
            //Copy card to fundamental pile
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1));
            //Set new pile number for card
            table.getTopCard_fromFundamentStack(match.toPile - 7).setBelongToPile(match.toPile);
            //Delete from old pile
            table.getPile(match.fromPile).remove(table.getPile(match.fromPile).size() - 1);
            //table.getFundamentPiles().get(toPile - 7).remove(table.getFundamentPiles().get(toPile - 7).size() - 1);
            //turnOverNextCard_inAPile(newCard, fromPile);
        }

    }

    @Override
    public void insertNextCardFromInput(Match match, Card card) {


        //If there is no match, and we wants to turn a card in the player deck.
        if (match.getFromPile() == 11 && !match.match){

            cardsLeft = (table.getPlayerDeck().size() - (table.getPlayerDeckIndex() + 1));
            //Check if there is more than 3 cards left to turn
            if(cardsLeft < 3 && cardsLeft != 0){
                List<Card> tempPile = new ArrayList<>();
                for (int i = 0 ; i < cardsLeft ; i++){
                    tempPile.add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
                    table.getPlayerDeck().remove(table.getPlayerDeckIndex());
                }
                for (int i = 0 ; i < cardsLeft ; i++){
                    table.getPlayerDeck().add(i, tempPile.get(i));
                }
                table.setPlayerDeckIndex(-1);
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);   //Increment pointer with 3
            }
            else if(cardsLeft == 0){
                table.setPlayerDeckIndex(-1);
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);   //Increment pointer with 3
            }
            else {
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);   //Increment pointer with 3
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(card.getColor());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(card.getValue());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(card.getType());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);
            }
        }
        //If the card from the player deck is a match to the toblou piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile < 7){
            //Move the card from player pile to table
            table.getPile(match.toPile).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());

            table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);

            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(card.getColor());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(card.getValue());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(card.getType());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);


        }

        //If the card from the player deck is a match to the foundation piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile > 7){
            //Move the card from player pile to foundation
            table.getFundamentPiles().get(match.toPile - 7).add(card);
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());

            table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(card.getColor());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(card.getValue());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(card.getType());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);

        }

        else {
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setColor(card.getColor());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setType(card.getType());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setValue(card.getValue());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setFaceUp(true);
        }
    }
}
