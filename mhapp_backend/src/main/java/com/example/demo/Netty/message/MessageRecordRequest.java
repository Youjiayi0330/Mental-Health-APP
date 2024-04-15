package com.example.demo.Netty.message;

import lombok.Data;

@Data
public class MessageRecordRequest extends MyMessage{
    private String fromUserId, toUserId;

    public MessageRecordRequest(String fromUserId, String toUserId){
        messageType = getMessageType();
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public MessageRecordRequest(){
        this.messageType = getMessageType();
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public int getMessageType() {
        return MessageType.MessageRecordRequest;
    }
}
