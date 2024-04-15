package com.example.mentalhealth.netty;

import android.os.Handler;

public class AllHandler {
    //主界面登录的回调
    public static Handler mainHandler;
    //注册用户的handler
    public static Handler registerHandler;
    //动态获取聊天记录的handler
    public static Handler chatRecordHandler;
    //消息界面的回调
    public static Handler messageHandler;
}
