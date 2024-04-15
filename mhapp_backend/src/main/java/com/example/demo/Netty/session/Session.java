package com.example.demo.Netty.session;

import com.example.demo.entity.Person;
import io.netty.channel.Channel;

public interface Session {
    //绑定会话
    void bind(Channel channel, Person person);
    //解绑
    void unbind(Channel channel);
    //根据用户id获取channel
    Channel getChannel(String loginId);
    //获取和设置属性
    Object getAttribute(Channel channel,String loginId);
    void setAttribute(Channel channel,String loginId,Object value);
    //获得登录人数
    Integer getVisitNum();
}
