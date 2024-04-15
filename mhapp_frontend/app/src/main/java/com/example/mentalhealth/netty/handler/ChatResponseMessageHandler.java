package com.example.mentalhealth.netty.handler;

import android.os.Message;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.message.ChatResponseMessage;
import com.example.mentalhealth.netty.pojo.ChatRecord;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatResponseMessageHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        ChatRecord record = msg.getRecord();
        System.out.println("我接收到"+record.getPersonId()+"发送给我"+record.getMessage());
        Message message = new Message();
        Message message1 = new Message();
        message.what = 1;
        message.obj = record;
        message1.what = 1;
        message1.obj = record;
        try{
            if(AllHandler.messageHandler != null){
                AllHandler.messageHandler.sendMessage(message1);
            }
            if(AllHandler.chatRecordHandler != null)
                AllHandler.chatRecordHandler.sendMessage(message);
        }catch(Exception e){
            System.out.println("收到消息时发生异常");
            e.printStackTrace();
        }
    }
}
