package com.example.mentalhealth.netty.handler;

import android.os.Message;
import android.util.Log;

import com.example.mentalhealth.fragment.MessageFG;
import com.example.mentalhealth.netty.AllHandler;
import com.example.mentalhealth.netty.AppInfo;
import com.example.mentalhealth.netty.message.LoginResponseMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginResponseMessageHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseMessage msg) throws Exception {
        Message m = new Message();
        if(msg.isSuccess()){
            m.what = 0;
            m.obj = msg.getReason();
            AppInfo.personNow = msg.getPerson();
            AllHandler.mainHandler.sendMessage(m);
            Log.d("Login",AppInfo.personNow.getNickname()+"登录成功");
        } else {
            m.what = 1;
            m.obj = msg.getReason();
            AllHandler.mainHandler.sendMessage(m);
            Log.d("Login","登录失败");
        }
        ctx.fireChannelRead(msg);

//        m.what = 0;
//        System.out.println(msg);
//        if(msg.isSuccess()) {
//            System.out.println("登录成功");
//            m.obj = msg.getReason();
//            AppInfo.personNow = msg.getPerson();
//            AllHandler.mainHandler.sendMessage(m);
//        } else {
//            System.out.println("登录失败");
//            m.obj = msg.getReason();
//            AllHandler.mainHandler.sendMessage(m);
//        }
//        ctx.fireChannelRead(msg);
    }
}
