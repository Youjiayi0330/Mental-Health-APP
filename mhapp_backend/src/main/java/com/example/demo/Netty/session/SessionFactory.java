package com.example.demo.Netty.session;

public class SessionFactory {
    private static Session session = new SessionMemoryImpl();

    public static Session getSession(){
        return session;
    }
}
