package com.example.mentalhealth.netty;

import com.example.mentalhealth.netty.pojo.Person;

import io.netty.channel.Channel;


//APP全局消息
public class AppInfo {
    //连接状态
    public static boolean isConnected=false;
    //当前连接的人
    public static Person personNow=null;
    //它对应的channel
    public static Channel clientChannel =null;

    public static final String SERVER_IP = "172.20.10.10";
    public static final int SERVER_PORT = 7000;

    public static final String IP = "http://172.20.10.10:8081/popmh/";
}
