package com.example.mentalhealth.netty.handler;

import android.os.Message;

import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.message.DataResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DataResponseHandler extends SimpleChannelInboundHandler<DataResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataResponse msg) throws Exception {
        System.out.println("一共" + msg.getData().size() + "条数据" + msg.getData());
        Message message = new Message();
        message.what = 0;
        message.obj = msg.getData();
        AllHandler.messageHandler.sendMessage(message);
    }
}
