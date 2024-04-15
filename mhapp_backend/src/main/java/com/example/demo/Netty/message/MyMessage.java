package com.example.demo.Netty.message;

public abstract class MyMessage {
    public int sequenceId;
    public int messageType;

    //由具体的子类Message实现，每一条子类对应上面的一个码子
    public abstract int getMessageType();

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    Class<?> getImplClass() {
        return this.getClass();
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
