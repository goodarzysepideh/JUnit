package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketMain {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        Socket socket = server.accept();

        int b = socket.getInputStream().read();
        System.out.println("rec data:" + b);
        socket.close();
        server.close();
    }
}
