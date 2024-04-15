package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.MessageRecordRequest;
import com.example.demo.Netty.message.MessageRecordResponse;
import com.example.demo.entity.ChatRecord;
import com.example.demo.service.ChatRecordService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
@ChannelHandler.Sharable
public class ChatRecordRequestMessageHandler extends SimpleChannelInboundHandler<MessageRecordRequest> {
    @Resource
    private ChatRecordService chatRecordService;

    public static ChatRecordRequestMessageHandler chatRecordRequestMessageHandler;

    @PostConstruct
    public void init(){
        chatRecordRequestMessageHandler = this;
        chatRecordRequestMessageHandler.chatRecordService = this.chatRecordService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRecordRequest messageRecordRequest) throws Exception {
//        try {
//            System.out.println("获取聊天记录中...");
//            List<ChatRecord> chatRecords = chatRecordRequestMessageHandler.chatRecordService
//                    .getChatRecord(messageRecordRequest.getFromUserId(),messageRecordRequest.getToUserId());
//            MessageRecordResponse messageRecordResponse = new MessageRecordResponse(chatRecords,true,"success");
//            ctx.writeAndFlush(messageRecordResponse);
//        } catch (Exception e){
//            e.printStackTrace();
//            ctx.writeAndFlush(new MessageRecordResponse(false,"请求聊天异常"));
//        }
        System.out.println("获取聊天记录中...");
        List<ChatRecord> chatRecords = chatRecordRequestMessageHandler.chatRecordService
                .getChatRecord(messageRecordRequest.getFromUserId(),messageRecordRequest.getToUserId());
        chatRecordRequestMessageHandler.chatRecordService.setHasRead(messageRecordRequest.getFromUserId(),messageRecordRequest.getToUserId());
        MessageRecordResponse messageRecordResponse = new MessageRecordResponse(chatRecords,true,"success");
        ctx.writeAndFlush(messageRecordResponse);
    }
}
