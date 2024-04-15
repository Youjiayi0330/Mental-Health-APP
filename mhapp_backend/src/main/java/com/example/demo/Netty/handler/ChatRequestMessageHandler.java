package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.ChatRequestMessage;
import com.example.demo.Netty.message.ChatResponseMessage;
import com.example.demo.Netty.session.SessionFactory;
import com.example.demo.entity.ChatRecord;
import com.example.demo.service.ChatRecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Resource
    private ChatRecordService chatRecordService;

    public static ChatRequestMessageHandler chatRequestMessageHandler;

    @PostConstruct
    public void init(){
        chatRequestMessageHandler = this;
        chatRequestMessageHandler.chatRecordService = this.chatRecordService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        String to = msg.getTo();
        //将发送的消息保存到数据库中
        ChatRecord chatRecord = new ChatRecord(from,to,0,new Date(),msg.getContent());
        //取到对方的通道
        Channel channel = SessionFactory.getSession().getChannel(to);
        if(channel!=null){
            log.debug("服务器捕获到:{}用户发送给{}一条消息:{}", from, to,msg);
            channel.writeAndFlush(new ChatResponseMessage(chatRecord));
            //chatRecord.setHasRead(1);
        } else {
            log.debug("服务器捕获到:{}用户发送给{}一条消息,但是{}不在线或者是没有此用户", from, to, to);
        }
        chatRequestMessageHandler.chatRecordService.addChatRecord(chatRecord);
    }
}
