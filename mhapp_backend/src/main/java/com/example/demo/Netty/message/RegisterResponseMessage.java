package com.example.demo.Netty.message;

import lombok.Data;

@Data
public class RegisterResponseMessage extends AbstractResponseMessage{
    public RegisterResponseMessage(boolean success, String reason) {
        super(success, reason);
        messageType=getMessageType();
    }

    public RegisterResponseMessage() {
        this.messageType=getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.RegisterResponseMessage;
    }
}
