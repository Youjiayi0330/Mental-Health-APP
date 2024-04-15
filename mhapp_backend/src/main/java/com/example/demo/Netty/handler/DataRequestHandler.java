package com.example.demo.Netty.handler;

import com.example.demo.Netty.message.DataRequest;
import com.example.demo.Netty.message.DataResponse;
import com.example.demo.service.PersonService;
import com.example.demo.vo.ChatListVo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
@ChannelHandler.Sharable
@Slf4j
public class DataRequestHandler extends SimpleChannelInboundHandler<DataRequest> {
    @Resource
    PersonService personService;

    public static DataRequestHandler dataRequestHandler;

    @PostConstruct
    public void init(){
        dataRequestHandler = this;
        dataRequestHandler.personService = this.personService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataRequest dataRequest) throws Exception {
        System.out.println(dataRequest.getPersonId());
        List<ChatListVo> chatListVoList = dataRequestHandler.personService.getChatFriends(dataRequest.getPersonId());
        ctx.writeAndFlush(new DataResponse(chatListVoList));
    }
}
