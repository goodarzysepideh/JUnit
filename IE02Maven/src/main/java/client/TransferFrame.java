package client;

import common.TransferBean;
import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public class TransferFrame extends JFrame {
    private static final Logger LOGGER =
            Logger.getLogger(TransferFrame.class.toString());
    private JTextField from;
    private JTextField to;
    private JTextField amount;
    private JTextField date;

//    private JButton datePicker;
    private JButton transfer;

    public TransferFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        from = new JTextField(20);
        to = new JTextField(20);
        amount = new JTextField(20);
        date = new JTextField(20);
//        datePicker = new JButton("...");
        transfer = new JButton("transfer");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel tempPanel = new JPanel(new FlowLayout());
        tempPanel.add(new JLabel("from     "));
        tempPanel.add(from);
        mainPanel.add(tempPanel);
        tempPanel = new JPanel(new FlowLayout());
        tempPanel.add(new JLabel("to        "));
        tempPanel.add(to);
        mainPanel.add(tempPanel);
        tempPanel = new JPanel(new FlowLayout());
        tempPanel.add(new JLabel("amount"));
        tempPanel.add(amount);
        mainPanel.add(tempPanel);
        tempPanel = new JPanel(new FlowLayout());
        tempPanel.add(new JLabel("date     "));
        tempPanel.add(date);
//        tempPanel.add(datePicker);
        mainPanel.add(tempPanel);

        final UtilDateModel model = new UtilDateModel();
        model.setValue(new Date());
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        mainPanel.add(datePanel);
        datePanel.addActionListener(new ActionListener() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                date.setText(model.getValue().toString());
            }
        });

        mainPanel.add(transfer);

        date.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilDateModel model = new UtilDateModel();
                Properties p = new Properties();
                p.put("text.today", "Today");
                p.put("text.month", "Month");
                p.put("text.year", "Year");
                JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
                final JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
                datePanel.add(datePicker);
                JPopupMenu pop = new JPopupMenu();
                pop.add(datePanel);
                pop.show(TransferFrame.this, date.getX(), date.getY() + date.getHeight());
            }
        });
        transfer.addActionListener(new AbstractAction() {
            //@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TransferBean dataBean = new TransferBean();
                    dataBean.setAmount(Long.parseLong(amount.getText()));
                    dataBean.setDate(date.getText());
                    dataBean.setFrom(from.getText());
                    dataBean.setTo(to.getText());
                    Socket socket = new Socket("localhost", 8080);
                    LOGGER.info("data is ready to send");
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    LOGGER.info("data is sent");
                    oos.writeObject(dataBean);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object result = ois.readObject();
                    System.out.println(result);
                    oos.close();
                    socket.close();
                } catch (IOException e1) {
                    LOGGER.error("", e1);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
        });
        this.getContentPane().add(mainPanel);
        this.pack();
    }

    
    class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
