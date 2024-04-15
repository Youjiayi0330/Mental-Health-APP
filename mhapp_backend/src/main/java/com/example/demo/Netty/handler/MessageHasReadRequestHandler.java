package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.MessageHasReadRequest;
import com.example.demo.service.ChatRecordService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
@ChannelHandler.Sharable
public class MessageHasReadRequestHandler extends SimpleChannelInboundHandler<MessageHasReadRequest> {
    @Resource
    private ChatRecordService chatRecordService;

    public static MessageHasReadRequestHandler messageHasReadRequestHandler;

    @PostConstruct
    public void init(){
        messageHasReadRequestHandler = this;
        messageHasReadRequestHandler.chatRecordService = this.chatRecordService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageHasReadRequest msg) throws Exception {
        messageHasReadRequestHandler.chatRecordService.setHasRead(msg.getUserId(),msg.getFriendId());
    }
}
