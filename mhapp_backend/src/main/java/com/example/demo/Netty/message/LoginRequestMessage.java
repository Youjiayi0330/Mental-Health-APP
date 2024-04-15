package com.example.demo.Netty.message;

import lombok.Data;

@Data
public class LoginRequestMessage extends MyMessage {
    private String loginId;
    private String loginPwd;

    @Override
    public int getMessageType() {
        return MessageType.LoginRequestMessage;
    }
}
