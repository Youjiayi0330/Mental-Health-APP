package com.example.demo.Netty.message;

public class MessageType {
    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;

    public static final int RegisterRequestMessage = 2;
    public static final int RegisterResponseMessage = 3;

    public static final int MessageRecordRequest = 4;
    public static final int MessageRecordResponse = 5;

    public static final int ChatRequestMessage = 6;
    public static final int ChatResponseMessage = 7;

    public static final int MessageHasReadRequest = 8;

    public static final int DataRequest = 9;
    public static final int DataResponse = 10;

    public static Class<? extends MyMessage> getMessageClass(int type){
        switch (type){
            case RegisterRequestMessage:
                return RegisterRequestMessage.class;
            case RegisterResponseMessage:
                return RegisterResponseMessage.class;
            case LoginRequestMessage:
                return LoginRequestMessage.class;
            case LoginResponseMessage:
                return LoginResponseMessage.class;
            case MessageRecordRequest:
                return MessageRecordRequest.class;
            case MessageRecordResponse:
                return MessageRecordResponse.class;
            case ChatRequestMessage:
                return ChatRequestMessage.class;
            case ChatResponseMessage:
                return ChatResponseMessage.class;
            case MessageHasReadRequest:
                return MessageHasReadRequest.class;
            case DataRequest:
                return DataRequest.class;
            case DataResponse:
                return DataResponse.class;
            default:
                System.out.println("MessageType没取到...");
                return null;
        }
    }
}
