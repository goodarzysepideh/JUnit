package server;

import common.TransferBean;
import common.TransferResponseBean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        ServerSocket server = new ServerSocket(8080);
        Socket socket ;
        while((socket = server.accept()) != null) {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            TransferBean request = (TransferBean) ois.readObject();
            if (!request.getFrom().startsWith("1") &&
                    !request.getTo().startsWith("1")) {
                TransferResponseBean trb = new TransferResponseBean();
                trb.setResult(false);
                trb.setMessage("Invalid account id");
                oos.writeObject(trb);
            } else {
                TransferResponseBean trb = new TransferResponseBean();
                trb.setResult(true);
                trb.setMessage("Transfered succesfully!");
                oos.writeObject(trb);
            }
            ois.close();
            oos.close();
        }
        socket.close();
        server.close();
    }

    private static Date parseDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
        return format.parse(date);
    }
}
