package com.example.demo.Netty.message;

import com.example.demo.vo.ChatListVo;
import lombok.Data;

import java.util.List;

@Data
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

}
