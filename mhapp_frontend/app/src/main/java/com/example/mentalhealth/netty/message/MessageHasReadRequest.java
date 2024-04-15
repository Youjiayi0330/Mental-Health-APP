package com.example.mentalhealth.netty.message;

import lombok.Data;

@Data
public class MessageHasReadRequest extends MyMessage{
    private String userId, friendId;

    public MessageHasReadRequest(String userId, String friendId){
        messageType = this.getMessageType();
        this.userId = userId;
        this.friendId = friendId;
    }

    public MessageHasReadRequest(){
        this.messageType = this.getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.MessageHasReadRequest;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
