package src;

import src.Interfaces.Solver;
import src.Interfaces.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm implements Solver {

    private int cardFromPile;// = -10;
    private int cardToPile;// = -10;
    private int cardFromComplexPileIndex;
    private boolean complexMatch = false;
    private  boolean printTable;
    private boolean turnOverCard_playerDeck = false;
    private boolean firstTurn = true;
    List<List<Card>> tempPile = new ArrayList<>();
    List<List<Card>> sortedList = new ArrayList<>();

    Table table;

    Algorithm(Table table){
        this.table = table;
    }

    public List<List<Card>> sortList(List<List<Card>> listToSort) {
        /*
         * @void
         * Takes a list and sort it in accenting order
         * -Create current and next index
         * -Compare the value of the current and next card.
         * -If next is smaller than current, swap it.
         * -Iterate through the list.
         * -Start over, if no swaps is done, set the boolean to false, and the loop is broken.
         * */
        boolean swapped = true;
        while (swapped)
        {
            swapped = false;
            int current = 0;
            int next = 1;
            while (next < listToSort.size())
            {
                if(listToSort.get(current).isEmpty() || listToSort.get(next).isEmpty()){current++; next++; continue;}
                if(listToSort.get(current).get(0).getValue() > listToSort.get(next).get(0).getValue())
                {
                    Collections.swap(listToSort, current, next);
                    swapped = true;
                }
                current++;
                next++;
            }
        }
        return listToSort;

       //List<List<Card>> sorted = listToSort.stream().sorted(Comparator.comparing(List<List<Card>>::get())).collect(Collectors.toList());
    }

    private void createSortedList_OfCards(){        //TODO Lav disse to finctioner createSortedList_OfCards() & sortList mere overskuelige
        /*
         * Initialize the temporary pile of cars and create a sorted list from low value to high value
         * */
        tempPile.clear();   //Clear any existing instance of the list
        tempPile.addAll(table.getAllFaceUpCards());
        sortedList = sortList(tempPile);
    }
    @Override
    public Match checkForAnyMatch() {

        //TODO implement complex match

        if(checkForMatch_TopPile()){
            return new Match(cardFromPile, cardToPile, true, false);
        }

        else if(checkForMatchBottomPiles()){
            return new Match(cardFromPile, cardToPile, true, false);
        }

        else if(checkForMatch_playerDeck()){
            return new Match(cardFromPile, cardToPile, true, false);
        }

        else if(checkForComplexMatch()){
            return new Match(cardFromPile, cardToPile, true, true, cardFromComplexPileIndex);
        }

        return new Match(11, -1, false, false);
    }


    private boolean checkForMatchBottomPiles() {

        createSortedList_OfCards(); //Re-init the temp pile, so that we get the cards back in
        while (!sortedList.isEmpty())
        {

//            if(checkKingCondition()){return true;};
//            createSortedList_OfCards();
            int validValue = 0;
            int validColor = 0;
            if(!sortedList.get(0).isEmpty()) {
                validValue = (sortedList.get(0).get(0).getValue()) + 1;
                if (sortedList.get(0).get(0).getColor() == 0) {
                    validColor = 1;
                }
            }
            for (int j = 0 ; j < sortedList.size(); j++)
            {
                if(sortedList.get(j).isEmpty()){continue;}
                if(sortedList.get(j).get(sortedList.get(j).size() - 1).getColor() == validColor && sortedList.get(j).get(sortedList.get(j).size() - 1).getValue() == validValue){
                    cardFromPile = sortedList.get(0).get(0).getBelongToPile();
                    cardToPile = sortedList.get(j).get(0).getBelongToPile();
                    return true;
                }
            }
            sortedList.remove(0);
        }
        return false;

    }

    private boolean checkForMatch_playerDeck() {
//Check for match in top piles,
        if(table.getPlayerDeck_FaceUp().size() == 0){return false;}
        for (int i = 0; i < 4; i++) {
            if (table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue() == table.getTopCard_fromFundamentStack(i).getValue() + 1 && table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getType() == table.getTopCard_fromFundamentStack(i).getType()) {
                cardFromPile = 11;
                cardToPile = i + 7;
                return true;
            }
        }
//Check for match tablou piles
        for (int i = 0; i < table.getAllPiles().size(); i++) {
           // if(table.getTopCard_PlayerDeck().getValue() == 1) {break;}  //The algorithm don't want to place a two on a tablou pile, as it is then locked
            if(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue() == 1) {break;}
            if(table.getPile(i).size() == 0){continue;}
            if (table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue() + 1 == table.getPile(i).get(table.getPile(i).size() - 1).getValue() && table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getColor() != table.getPile(i).get(table.getPile(i).size() - 1).getColor()) {
                cardFromPile = 11;
                cardToPile = i;
                return true;
            }
        }

//Check for king to move onto empty pile
        //TODO perhaps check for the dept
        if(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1).getValue() == 12 && checkForAnyEmptyPile()) {
            if(kingHasMatch(table.getPlayerDeck_FaceUp().get(table.getPlayerDeck_FaceUp().size() - 1)))
            {
                cardFromPile = 11;
                for(int i = 0 ; i < 7 ; i++){
                    if(table.getAllPiles().get(i).isEmpty()){
                        cardToPile = i;
                    }
                }
                return true;
            }
        }
        return false;
    }

    private boolean kingHasMatch(Card king) {   //TODO maybe improve this
        int validValue = 11;
        int validColor = 0;

        if(king.getColor() == 0)
        {
            validColor = 1;
        }

        for (int i = 0 ; i < table.getAllPiles().size() ; i++)
        {
            if(table.getBottomFaceUpCard_FromPile(i).getColor() == validColor && table.getBottomFaceUpCard_FromPile(i).getValue() == validValue)
            {
                return true;
            }
        }
        //TODO remember the cards in the players pile, and move the king, if there is a match there as well.
        return false;
    }

    private boolean checkForAnyEmptyPile() {
        for (int i = 0 ; i < 7 ; i++) {
            if (table.getAllPiles().get(i).size() == 0) {
                return true;
            }
        }
        return false;
    }


    private boolean checkForMatch_TopPile() {
        createSortedList_OfCards();
        //Check for simple match only at the top card in a pile
        while (!sortedList.isEmpty())
        {
            for (int j = 0 ; j < 4 ; j++)
            {
                if(sortedList.get(0).isEmpty()){continue;}
                if(sortedList.get(0).get(sortedList.get(0).size() - 1).getValue() == table.getTopCard_fromFundamentStack(j).getValue() + 1
                        && sortedList.get(0).get(sortedList.get(0).size() - 1).getType() == table.getTopCard_fromFundamentStack(j).getType())
                {
                    cardFromPile = sortedList.get(0).get(0).getBelongToPile();
                    cardToPile = 7 + j;
                    //System.out.println("Hit in a top deck");
                    return true;
                }
            }
            sortedList.remove(0);
        }
        //Check for complex match where the algorithm breaks a pile up to make a match
        //return checkForComplexMatch();
        return false;
    }

    private boolean indexCanSplit(Card card) {
        //createSortedList_OfCards();
        int validValue = card.getValue() + 1;
        int validColor = 0;
        if(card.getColor() == 0){validColor = 1;}
        for(int i = 0 ; i < table.getAllPiles().size() ; i++)
        {
            //System.out.println("Test2");
            //if(sortedList.get(0).isEmpty())
            if(table.getPile(i).isEmpty())
            {
                continue;
            }
            if (table.getPile(i).get(table.getPile(i).size() - 1).getValue() == validValue && table.getPile(i).get(table.getPile(i).size() - 1).getColor() == validColor)
            {
                cardToPile = i;
                return true;
            }
        }
        return false;
    }

    private boolean checkForComplexMatch() {
        /*
         * This function will see, if there is any open-faced card that can be a potential match in the foundation piles.
         * If so, then it will also check, if it is possible to move the card on top of that card, to somewhere else, in order for
         * the card to become free.
         *
         * - Store the valid value and type with respect to foundation pile
         * - Run through all the cards, but skip the face-down cards
         * - If a card matches the value stored, check if the pile can be split with the indexCanSplit() function
         * - If indexCanSplit() returns true, store the relevant index and piles, and return true-
         * */
        for (int i = 0 ; i < 4 ; i++)   //Check all four fundament pile
        {
            int validValue = table.getTopCard_fromFundamentStack(i).getValue() + 1;
            int validType = table.getTopCard_fromFundamentStack(i).getType();
            for(int j = 0 ; j < table.getAllPiles().size() ; j++)
            {
                for (int k = 0 ; k < table.getPile(j).size() ; k++)
                {
                    if(!table.getPile(j).get(k).isFaceUp()){continue;}
                    if(table.getPile(j).get(k).getValue() == validValue && table.getPile(j).get(k).getType() == validType)
                    {
                        if(indexCanSplit(table.getPile(j).get(k+1)) ) //See if we can move the card on top of the indexed card
                        {
                            cardFromPile = j;
                            cardFromComplexPileIndex = k + 1;
                            //cardHandler.setComplexMatch(true);  //This is a flag, telling the card handler, that it has to split piles
                            table.setComplexSplitIndex(cardFromComplexPileIndex);
                            complexMatch = true;
                            //if (printTable)
                                //System.out.println("SPLIT PILE " + cardFromPile + " at card value: " + cardHandler.getPile(j).get(k+1).getValue() + " type: " + cardHandler.getPile(j).get(k+1).getType() + " to pile: " + cardToPile );
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
