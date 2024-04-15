package com.example.mentalhealth.netty.message;

import com.example.mentalhealth.netty.pojo.ChatListVo;

import java.util.List;

public class DataResponse extends MyMessage{
    private List<ChatListVo> data;

    public DataResponse(){
        this.messageType = getMessageType();
    }

    public DataResponse(List<ChatListVo> data){
        this.messageType = getMessageType();
        this.data = data;
    }

    @Override
    public int getMessageType() {
        return MessageType.DataResponse;
    }

    public List<ChatListVo> getData() {
        return data;
    }

    public void setData(List<ChatListVo> data) {
        this.data = data;
    }
}
