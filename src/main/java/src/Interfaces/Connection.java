package src.Interfaces;

import java.io.IOException;

public interface Connection {

    void startSocket(int port) throws IOException;

    String waitForNextCard();


}
