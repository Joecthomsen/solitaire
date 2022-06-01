package src.Interfaces;

import src.Interfaces.Connection;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class Socket implements Connection {

    //initialize socket and input stream
    private java.net.Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    //ServerSocket serverSocket = new ServerSocket(port);


    public Socket(int port) throws IOException {
        server = new ServerSocket(port);
        System.out.println("Server started");
        System.out.println("Waiting for client..");
        socket = server.accept();
        System.out.println("Client accepted");

        in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        String line = "";

        // reads message from client until "Over" is sent
        while (!line.equals("Over"))
        {
            try
            {
                line = in.readUTF();
                System.out.println(line);

            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
        System.out.println("Closing connection");

        // close connection
        socket.close();
        in.close();
}


    @Override
    public void startSocket(int port) throws IOException {

    }

    @Override
    public String waitForNextCard() {
        return null;
    }
}
