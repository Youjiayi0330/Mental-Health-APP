package com.example.mentalhealth.netty.message;

import com.example.mentalhealth.netty.pojo.ChatRecord;

import java.util.List;

import lombok.Data;

@Data
public class MessageRecordResponse extends AbstractResponseMessage{
    List<ChatRecord> record;

    public MessageRecordResponse(List<ChatRecord> record,boolean success, String reason) {
        super(success, reason);
        messageType=getMessageType();
        this.record=record;
    }

    public MessageRecordResponse() {
        this.messageType=getMessageType();
    }

    public MessageRecordResponse(boolean success, String reason) {
        super(success, reason);
        messageType=getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.MessageRecordResponse;
    }

    public List<ChatRecord> getRecord() {
        return record;
    }

    public void setRecord(List<ChatRecord> record) {
        this.record = record;
    }

}
