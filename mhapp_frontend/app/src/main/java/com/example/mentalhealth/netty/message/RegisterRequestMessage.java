package com.example.mentalhealth.netty.message;

import com.example.mentalhealth.netty.pojo.Person;

import lombok.Data;

@Data
public class RegisterRequestMessage extends MyMessage{
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

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
