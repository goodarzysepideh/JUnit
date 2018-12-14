package server;

import common.TransferBean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ElementaryTesting {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TransferBean tb = new TransferBean();
        tb.setDate(",,,");
        tb.setFrom("1234");
        tb.setTo("4321");
        tb.setAmount(11);

        Socket socket = new Socket("localhost", 8080);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(tb);
        Object result = new ObjectInputStream(socket.getInputStream()).readObject();
        System.out.println(result);

        //...Other test cases4
    }
}
