package com.example.mentalhealth.netty.message;

public class LoginRequestMessage extends  MyMessage{

    private String loginId,loginPwd;

    @Override
    public int getMessageType() {
        return MessageType.LoginRequestMessage;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }
}
