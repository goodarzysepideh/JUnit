package server;

import common.TransferBean;
import common.TransferResponseBean;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerMainTest {

    Socket socket;
    @org.junit.Before
    public void setUp() throws Exception {
        //Connecting to the server
        socket = new Socket("localhost", 8080);

    }

    @org.junit.After
    public void tearDown() throws Exception {
        //Closing the streams and connections
        socket.close();
    }

    @Test
    public void test1() throws IOException, ClassNotFoundException {
        TransferBean tb = new TransferBean();
        tb.setDate(",,,");
        tb.setFrom("1234");
        tb.setTo("4321");
        tb.setAmount(11);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(tb);
        TransferResponseBean tbr = new TransferResponseBean();
        tbr.setResult(false);
        tbr.setMessage("Invalid account id");
        assertEquals(tbr, new ObjectInputStream(socket.getInputStream()).readObject());
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException{
        TransferBean tb = new TransferBean();
        tb.setDate(",,,");
        tb.setFrom("4234");
        tb.setTo("1321");
        tb.setAmount(11);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(tb);
        TransferResponseBean tbr = new TransferResponseBean();
        tbr.setResult(false);
        tbr.setMessage("Invalid account id");
        assertEquals(tbr, new ObjectInputStream(socket.getInputStream()).readObject());
    }

    @Test
    public void test3() throws IOException, ClassNotFoundException{
        TransferBean tb = new TransferBean();
        tb.setDate(",,,");
        tb.setFrom("4234");
        tb.setTo("4321");
        tb.setAmount(11);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(tb);
        TransferResponseBean tbr = new TransferResponseBean();
        tbr.setResult(false);
        tbr.setMessage("Invalid account id");
        assertEquals(tbr, new ObjectInputStream(socket.getInputStream()).readObject());
    }

    @Test
    public void test4() throws IOException, ClassNotFoundException{
        TransferBean tb = new TransferBean();
        tb.setDate(",,,");
        tb.setFrom("1234");
        tb.setTo("1321");
        tb.setAmount(11);
        new ObjectOutputStream(socket.getOutputStream()).writeObject(tb);
        TransferResponseBean tbr = new TransferResponseBean();
        tbr.setResult(true);
        tbr.setMessage("Transfered succesfully!");
        assertEquals(tbr, new ObjectInputStream(socket.getInputStream()).readObject());
    }

}