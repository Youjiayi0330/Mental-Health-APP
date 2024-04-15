package com.example.demo.Netty.message;

import com.example.demo.entity.Person;
import lombok.Data;

@Data
public class LoginResponseMessage extends AbstractResponseMessage{
    private Person person;
    public LoginResponseMessage(Person person, boolean success, String reason) {
        super(success, reason);
        this.messageType=getMessageType();
        this.person=person;
        messageType=MessageType.LoginResponseMessage;
    }

    public LoginResponseMessage() {
        this.messageType = getMessageType();
    }

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return MessageType.LoginResponseMessage;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
