package common;

import java.io.Serializable;


public class TransferResponseBean implements Serializable {
    private boolean result;
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "result:" + result + ", message:" + message;
    }

    public boolean equals(Object obj){
        TransferResponseBean tbr = (TransferResponseBean)obj;
        return tbr.message.equals(this.message) && tbr.result == this.result ;
    }

}
