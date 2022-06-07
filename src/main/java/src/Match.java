package src;

public class Match {

    int fromPile;
    int toPile;
    boolean match;

    boolean complex = false;

    int complexIndex;

    Card nextPlayerCard;

    public Match(boolean match) {
        this.match = match;
    }

    public Match(int fromPile, int toPile, boolean match, boolean complex, int complexIndex) {
        this.fromPile = fromPile;
        this.toPile = toPile;
        this.match = match;
        this.complex = complex;
        this.complexIndex = complexIndex;
    }

    public Match(int fromPile, int toPile, boolean match, boolean complex) {
        this.fromPile = fromPile;
        this.toPile = toPile;
        this.match = match;
        this.complex = complex;
    }

    public Match(int fromPile, int toPile, boolean match, boolean complex, int complexIndex, Card nextPlayerCard) {
        this.fromPile = fromPile;
        this.toPile = toPile;
        this.match = match;
        this.complex = complex;
        this.complexIndex = complexIndex;
        this.nextPlayerCard = nextPlayerCard;
    }

    public int getFromPile() {
        return fromPile;
    }

    public void setFromPile(int fromPile) {
        this.fromPile = fromPile;
    }

    public int getToPile() {
        return toPile;
    }

    public void setToPile(int toPile) {
        this.toPile = toPile;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}
