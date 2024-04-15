package com.example.mentalhealth.netty.handler;

import android.os.Message;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.message.MessageRecordResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageRecordResponseHandler extends SimpleChannelInboundHandler<MessageRecordResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRecordResponse msg) throws Exception {
        //得到所有聊天记录
        Message message = new Message();
        message.what = 0;
        message.obj = msg.getRecord();
        AllHandler.chatRecordHandler.sendMessage(message);
    }
}
