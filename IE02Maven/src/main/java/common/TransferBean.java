package common;

import java.io.Serializable;


public class TransferBean implements Serializable {
    private String from, to, date;
    private long amount;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String toString() {
        return "Transfer " + amount + " dollars from " + from + " to " + to + " on " + date;
    }
}
