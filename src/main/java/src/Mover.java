package src;

import src.Interfaces.Move;
import src.Interfaces.Table;

import java.util.ArrayList;
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

//    //private void incrementPlayerDeckIndex(){
//        //cardsLeft = table.getPlayerDeck().size() - table.getPlayerDeckIndex();
//            //cardsLeft = table.getPlayerDeck_FaceUp().size() - table.getPlayerDeckIndex() - 1;
//            if(  < 3 && cardsLeft != 0){
//                List<Card> tempList = new ArrayList<>();
//                for (int i = 0 ; i < cardsLeft ; i++){
//                    tempList.add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeckIndex() + i));
//                }
//                for (int i = cardsLeft ; i >= 0 ; i--){
//                    table.getPlayerDeck_FaceUp().add(0, tempList.get(i));
//                }
//                table.setPlayerDeckIndex(2);
//                //TODO handle end of pile
//            }
//            else if(cardsLeft == 0 && table.getPlayerDeck_FaceUp().size() > 3){
//                table.setPlayerDeckIndex(2);
//            }
//            else{table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);}
//    }

//    private void decrementPlayerDeckIndex() {
//        table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
//        if(table.getPlayerDeckIndex() == -1){table.setPlayerDeckIndex(0);}
//    }

    @Override
    public void moveCard_OrPile(Match match) {

    //If we move from player pile to tablou pile
        if (match.fromPile == 11 && match.toPile < 7 && match.match)
        {
            //incrementPlayerDeckIndex();
            table.getAllPiles().get(match.toPile).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
        }
    //If we just turn over one card from player pile
//        else if(match.fromPile == 11 && !match.match)
//        {
//            if(!table.getPlayerDeck_FaceUp().get(table.getPlayerDeckIndex()).isFaceUp()) {
//                if (cardsLeft < 3 && cardsLeft != 0) {
//                    table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);
//                } else if (cardsLeft == 0 && table.getPlayerDeck_FaceUp().size() > 3) {
//                    table.setPlayerDeckIndex(-1);
//                }
//            }
//            //else{table.setPlayerDeckIndex(table.getPlayerDeckIndex() + 3);}
//            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeckIndex()));
//            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeckIndex());
//        }
    //If we move from player deck to foundation pile
        else if(match.match && match.fromPile == 11){   //If we move from player pile to foundation pile
            table.getFundamentPiles().get(match.toPile - 7).add(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);
            //decrementPlayerDeckIndex();
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
                table.getPlayerDeck_FaceUp().clear();
            }
            else{
                if (table.getPlayerDeck_FaceDown().size() == 0){
                    table.getPlayerDeck_FaceDown().addAll(table.getPlayerDeck_FaceUp());
                    table.getPlayerDeck_FaceUp().clear();
                    for (int i = 0; i < 3; i++) {
                        table.getPlayerDeck_FaceUp().add(table.getPlayerDeck_FaceDown().get(0));
                        table.getPlayerDeck_FaceDown().remove(0);
                    }
                    if (table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).isFaceUp()){
                        System.out.println("Card known: " +table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1));
                    }
                    else{
                        setNewCard(match);;
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
        //Move the card from player pile to foundation
            table.getFundamentPiles().get(match.toPile - 7).add(match.nextPlayerCard);
            table.getPlayerDeck_FaceUp().remove(table.getPlayerDeck_FaceUp().size() - 1);

            //table.setPlayerDeckIndex(table.getPlayerDeckIndex() - 1);
            setNewCard(match);


        }

        else if(match.match && match.fromPile < 7 && match.toPile < 7){

            table.getAllPiles().get(match.fromPile).add(match.nextPlayerCard);
            table.getAllPiles().get(match.fromPile).remove(table.getAllPiles().get(match.fromPile).size() - 2);
        }

    //If the match is from the tablou pile to another tablou pile (i think)
        else if(!table.getAllPiles().get(match.fromPile).isEmpty()) {
            setNewCard(match);
        }
        else System.out.println("EMPTY PILE!");
    }

    private void setNewCard(Match match) {
        if(!table.getPlayerDeck_FaceUp().isEmpty()) {
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setFaceUp(true);
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setColor(match.nextPlayerCard.getColor());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setValue(match.nextPlayerCard.getValue());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setType(match.nextPlayerCard.getType());
            table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).setBelongToPile(match.nextPlayerCard.getBelongToPile());
        }
    }
}
