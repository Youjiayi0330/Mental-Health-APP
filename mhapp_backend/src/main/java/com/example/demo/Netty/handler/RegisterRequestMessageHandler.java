package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.RegisterRequestMessage;
import com.example.demo.Netty.message.RegisterResponseMessage;
import com.example.demo.entity.Person;
import com.example.demo.service.PersonService;
import com.example.demo.service.impl.PersonServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class RegisterRequestMessageHandler extends SimpleChannelInboundHandler<RegisterRequestMessage> {

    @Resource
    private PersonService personService ;

    public static RegisterRequestMessageHandler registerRequestMessageHandler;

    @PostConstruct
    public void init(){
        registerRequestMessageHandler = this;
        registerRequestMessageHandler.personService = this.personService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestMessage registerRequestMessage) throws Exception {
        System.out.println("注册...");
        Person person = registerRequestMessage.getPerson();
        personService = new PersonServiceImpl();
        String s = registerRequestMessageHandler.personService.add(person);
        RegisterResponseMessage responseMessage = null;
        if("注册成功".equals(s)){
            responseMessage = new RegisterResponseMessage(true,s);
        }else {
            responseMessage = new RegisterResponseMessage(false,s);
        }
        ctx.writeAndFlush(responseMessage);
    }
}
