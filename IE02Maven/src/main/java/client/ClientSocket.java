package client;

import java.io.IOException;
import java.net.Socket;


public class ClientSocket {
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost", 8080);
        socket.getOutputStream().write(10);
        socket.close();
    }
}
