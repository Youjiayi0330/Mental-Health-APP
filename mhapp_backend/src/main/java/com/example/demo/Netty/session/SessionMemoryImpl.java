package com.example.demo.Netty.session;

import com.example.demo.entity.Person;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SessionMemoryImpl implements Session{
    private final Map<String, IoSession> loginIdIoSessionMap = new ConcurrentHashMap<>();
    private final Map<Channel, IoSession> channelIoSessionMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, Person person) {
        IoSession ioSession = new IoSession(channel,person);
        loginIdIoSessionMap.put(person.getId(), ioSession);
        channelIoSessionMap.put(channel, ioSession);
        log.debug("{}用户绑定session连接...",person.getNickname());
    }

    @Override
    public void unbind(Channel channel) {
        IoSession ioSession = channelIoSessionMap.remove(channel);
        if(ioSession == null){
            return;
        }
        loginIdIoSessionMap.remove(ioSession.getPerson().getId());
        ioSession.close();
        log.info("关闭{}的session连接", ioSession.getPerson().getNickname());
    }

    @Override
    public Channel getChannel(String loginId) {
        IoSession ioSession = loginIdIoSessionMap.get(loginId);
        if(ioSession == null){
            return null;
        }
        return ioSession.getChannel();
    }

    @Override
    public Object getAttribute(Channel channel, String id) {
        return channelIoSessionMap.get(channel).getAttribute(id);
    }

    @Override
    public void setAttribute(Channel channel, String id, Object value) {
        channelIoSessionMap.get(channel).setAttribute(id,value);
    }

    @Override
    public Integer getVisitNum() {
        return loginIdIoSessionMap.size();
    }
}
