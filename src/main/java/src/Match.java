package src;

public class Match {

    int fromPile;
    int toPile;
    boolean match;

    public Match(boolean match) {
        this.match = match;
    }

    public Match(int fromPile, int toPile, boolean match) {
        this.fromPile = fromPile;
        this.toPile = toPile;
        this.match = match;
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
