package com.example.demo.Netty.message;

import com.example.demo.entity.ChatRecord;
import lombok.Data;

import java.util.List;

@Data
public class MessageRecordResponse extends AbstractResponseMessage{
    List<ChatRecord> record;

    public MessageRecordResponse(List<ChatRecord> record, boolean success, String reason){
        super(success, reason);
        messageType = getMessageType();
        this.record = record;
    }

    public MessageRecordResponse(){
        this.messageType = getMessageType();
    }

    public MessageRecordResponse(boolean success, String reason) {
        super(success, reason);
        messageType=getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.MessageRecordResponse;
    }

}
