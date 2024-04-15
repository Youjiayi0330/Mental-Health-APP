package com.example.mentalhealth.netty.message;

import lombok.Data;

@Data
public class DataRequest extends MyMessage{
    String personId;

    public DataRequest() {
        this.messageType=getMessageType();

    }

    public DataRequest(String personId) {
        this.messageType=getMessageType();
        this.personId = personId;
    }

    @Override
    public int getMessageType() {
        return MessageType.DataRequest;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
