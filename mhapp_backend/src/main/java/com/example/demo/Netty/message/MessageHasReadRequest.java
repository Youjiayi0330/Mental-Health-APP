package com.example.demo.Netty.message;

import lombok.Data;

@Data
public class MessageHasReadRequest extends MyMessage{
    private String userId, friendId;

    public MessageHasReadRequest(String userId, String friendId){
        messageType = getMessageType();
        this.userId = userId;
        this.friendId = friendId;
    }

    public MessageHasReadRequest(){
        this.messageType = getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.MessageHasReadRequest;
    }
}
