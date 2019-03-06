package com.wce.tractorapp.model;

public class Chat {
    String senderUid;
    String receiverUid;
    long time;

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;

    public Chat(String senderUid, String receiverUid, long time, String text) {
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.time = time;
        this.text = text;
    }
}
