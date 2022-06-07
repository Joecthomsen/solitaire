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

    private void incrementPlayerDeckIndex(){
        cardsLeft = table.getPlayerDeck().size() - table.getPlayerDeckIndex();
            cardsLeft = table.getPlayerDeck().size() - table.getPlayerDeckIndex();
            if(cardsLeft < 3 && cardsLeft != 0){
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);
            }
            else if(cardsLeft == 0 && table.getPlayerDeck().size() > 3){
                table.setPlayerDeckIndex(2);
            }
            else{table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);}
            //TODO handle end of pile
    }

    private void decrementPlayerDeckIndex() {
        table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
        if(table.getPlayerDeckIndex() == -1){table.setPlayerDeckIndex(0);}
    }

    @Override
    public void moveCard_OrPile(Match match) {

    //If we move from player pile to tablou pile
        if (match.fromPile == 11 && match.toPile < 7 && match.match)
        {
            incrementPlayerDeckIndex();
            table.getAllPiles().get(match.toPile).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());
        }
    //If we just turn over one card from player pile
        else if(match.fromPile == 11 && !match.match)
        {
            if(cardsLeft < 3 && cardsLeft != 0){
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);
            }
            else if(cardsLeft == 0 && table.getPlayerDeck().size() > 3){
                table.setPlayerDeckIndex(-1);
            }
            else{table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);}
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());
        }
    //If we move from player deck to foundation pile
        else if(match.match && match.fromPile == 11){   //If we move from player pile to foundation pile
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());
            decrementPlayerDeckIndex();
        }
        else if(match.match && match.toPile < 7) {   //If we want to move the card to a bottom pile
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
    public void insertNextCardFromInput(Match match) {

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
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(match.nextPlayerCard.getColor());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(match.nextPlayerCard.getValue());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(match.nextPlayerCard.getType());
                table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);
            }
        }
        //If the card from the player deck is a match to the tablou piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile < 7){
            //Move the card from player pile to table
            table.getPile(match.toPile).add(table.getPlayerDeck().get(table.getPlayerDeckIndex()));
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());

            if(table.getPlayerDeckIndex() != 0) {
                table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
            }

//            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(card.getColor());
//            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(card.getValue());
//            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(card.getType());
//            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);


        }

    //If the card from the player deck is a match to the foundation piles, and we want to reveal the card underneath
        else if (match.getFromPile() == 11 && match.match && match.toPile >= 7){
        //Move the card from player pile to foundation
            table.getFundamentPiles().get(match.toPile - 7).add(match.nextPlayerCard);
            table.getPlayerDeck().remove(table.getPlayerDeckIndex());

            table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setColor(match.nextPlayerCard.getColor());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setValue(match.nextPlayerCard.getValue());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setType(match.nextPlayerCard.getType());
            table.getPlayerDeck().get(table.getPlayerDeckIndex()).setFaceUp(true);

        }
    //If the match is from the tablou pile to another tablou pile (i think)
        else if(!table.getAllPiles().get(match.fromPile).isEmpty()) {
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setColor(match.nextPlayerCard.getColor());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setType(match.nextPlayerCard.getType());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setValue(match.nextPlayerCard.getValue());
            table.getPile(match.fromPile).get(table.getPile(match.fromPile).size() - 1).setFaceUp(true);
        }
        else System.out.println("EMPTY PILE!");
    }
}
