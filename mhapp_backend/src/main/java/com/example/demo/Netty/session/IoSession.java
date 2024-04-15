package com.example.demo.Netty.session;

import com.example.demo.entity.Person;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IoSession {
    private Channel channel;
    private Person person;
    private Map<String, Object> attributes = new HashMap<>();

    //关闭session
    public void close() {
        try {
            if (this.channel == null) {
                return;
            }
            if (channel.isOpen()) {
                channel.close();
            }
        } catch (Exception e) {

        }
    }

    public IoSession(Channel channel, Person person) {
        this.channel = channel;
        this.person = person;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Object getAttribute(String key){
        return attributes.get(key);
    }
    public void setAttribute(String key,Object value){
        attributes.put(key,value);
    }
}
