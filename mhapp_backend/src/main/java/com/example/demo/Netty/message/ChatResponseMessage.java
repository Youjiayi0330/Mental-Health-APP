package com.example.demo.Netty.message;

import com.example.demo.entity.ChatRecord;
import lombok.Data;

@Data
public class ChatResponseMessage extends AbstractResponseMessage{
    private ChatRecord record;
    public ChatResponseMessage() {
        this.messageType=getMessageType();
    }

    public ChatResponseMessage(boolean success, String reason) {
        super(success, reason);
        this.messageType=getMessageType();
    }

    public ChatResponseMessage(ChatRecord record) {
        this();
        this.record=record;
    }

    @Override
    public int getMessageType() {
        return MessageType.ChatResponseMessage;
    }

    public ChatRecord getRecord() {
        return record;
    }

    public void setRecord(ChatRecord record) {
        this.record = record;
    }
}
