package com.wce.tractorapp.model;

public class Chat {
    String senderUid;
    String senderAvatarUrl;
    String reciverAvatarUrl;
    String receiverUid;
    String time;

    public Chat()
    {
        
    }

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderAvatarUrl() {
        return senderAvatarUrl;
    }

    public void setSenderAvatarUrl(String senderAvatarUrl) {
        this.senderAvatarUrl = senderAvatarUrl;
    }

    public String getReciverAvatarUrl() {
        return reciverAvatarUrl;
    }

    public void setReciverAvatarUrl(String reciverAvatarUrl) {
        this.reciverAvatarUrl = reciverAvatarUrl;
    }

    String text;

    public Chat(String senderUid, String receiverUid, String time, String text, String reciverAvatarUrl, String senderAvatarUrl) {
        this.senderUid = senderUid;
        this.receiverUid = receiverUid;
        this.time = time;
        this.text = text;
        this.reciverAvatarUrl = reciverAvatarUrl;
        this.senderAvatarUrl = senderAvatarUrl;
    }
}
