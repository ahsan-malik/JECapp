package j.e.c.com.chatFragments.models;

public class Message {
    private String message, sender, receiver, time;

    public Message(){}

    public Message(String message, String sender, String receiver, String time) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
