package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.LoginRequestMessage;
import com.example.demo.Netty.message.LoginResponseMessage;
import com.example.demo.Netty.session.SessionFactory;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import com.example.demo.service.impl.PersonServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    @Resource
    private PersonService personService;

    public static LoginRequestHandler loginRequestHandler;

    @PostConstruct
    public void init(){
        loginRequestHandler = this;
        loginRequestHandler.personService = this.personService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        System.out.println("登录中...");
        personService = new PersonServiceImpl();
        Person person = loginRequestHandler.personService.login(msg.getLoginId(),msg.getLoginPwd());
        //System.out.println(person.getNickname()+"登录中");
        LoginResponseMessage response;
        if(person != null) {
            System.out.println("用户信息搜索成功");
            response = new LoginResponseMessage(person, true,"登录成功");
            //绑定session会话管理
            SessionFactory.getSession().bind(ctx.channel(),person);
        } else {
            System.out.println("用户信息搜索失败");
            response = new LoginResponseMessage(false,"用户名或密码错误");
        }
        ctx.writeAndFlush(response);
    }
}
