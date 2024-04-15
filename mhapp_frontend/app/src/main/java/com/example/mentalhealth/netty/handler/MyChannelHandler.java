package com.example.mentalhealth.netty.handler;

import android.util.Log;

import com.example.mentalhealth.netty.AppInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        AppInfo.clientChannel = ctx.channel();
        AppInfo.isConnected = true;
        Log.d("Client",ctx.channel().localAddress()+"连接上"+ctx.channel().remoteAddress()+"服务器");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Log.d("Client"+ctx.channel().localAddress(),"与服务器连接断开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        Log.d("Client"+ctx.channel().localAddress(),"通道异常");
    }
}
