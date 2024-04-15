package com.example.demo.Netty.message;

import lombok.Data;

@Data
public class ChatRequestMessage extends MyMessage{
    private String content;
    private String to;
    private String from;

    private ChatRequestMessage() {
        messageType=getMessageType();
    }


    public ChatRequestMessage(String from, String to, String content) {
        this();
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public int getMessageType() {

        return MessageType.ChatRequestMessage;
    }
}
