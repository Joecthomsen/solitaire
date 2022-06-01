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
}
