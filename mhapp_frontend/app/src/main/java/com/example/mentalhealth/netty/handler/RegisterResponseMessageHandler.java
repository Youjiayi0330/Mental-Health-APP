package com.example.mentalhealth.netty.handler;

import android.os.Message;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.message.RegisterResponseMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RegisterResponseMessageHandler extends SimpleChannelInboundHandler<RegisterResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponseMessage msg) throws Exception {
        Message message = new Message();
        message.what = 0;
        message.obj = msg;
        AllHandler.registerHandler.sendMessage(message);
    }
}
