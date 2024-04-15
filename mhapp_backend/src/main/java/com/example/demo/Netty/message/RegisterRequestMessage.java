package com.example.demo.Netty.message;

import com.example.demo.entity.Person;
import io.netty.channel.ChannelHandler;
import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;

@Data
public class RegisterRequestMessage extends MyMessage {
    private Person person;

    public RegisterRequestMessage(Person person) {
        this.person = person;
    }

    public RegisterRequestMessage() {
        this.messageType=getMessageType();
    }

    @Override
    public int getMessageType() {
        return MessageType.RegisterRequestMessage;
    }
}
