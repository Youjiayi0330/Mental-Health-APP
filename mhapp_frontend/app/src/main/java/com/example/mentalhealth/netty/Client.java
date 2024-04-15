package com.example.mentalhealth.netty;

import com.example.mentalhealth.netty.codec.MessageCodec;
import com.example.mentalhealth.netty.handler.ChatResponseMessageHandler;
import com.example.mentalhealth.netty.handler.DataResponseHandler;
import com.example.mentalhealth.netty.handler.LoginResponseMessageHandler;
import com.example.mentalhealth.netty.handler.MessageRecordResponseHandler;
import com.example.mentalhealth.netty.handler.MyChannelHandler;
import com.example.mentalhealth.netty.handler.RegisterResponseMessageHandler;
import com.example.mentalhealth.netty.message.RegisterRequestMessage;
import com.example.mentalhealth.netty.pojo.Person;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client extends  Thread{
    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(16777216, 9, 4, 3, 0));
                            ch.pipeline().addLast(new MessageCodec());
                            ch.pipeline().addLast(new MyChannelHandler());
                            ch.pipeline().addLast(new LoginResponseMessageHandler());
                            ch.pipeline().addLast(new MessageRecordResponseHandler());
                            ch.pipeline().addLast(new ChatResponseMessageHandler());
                            ch.pipeline().addLast(new RegisterResponseMessageHandler());
                            ch.pipeline().addLast(new DataResponseHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(AppInfo.SERVER_IP, AppInfo.SERVER_PORT)).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            AppInfo.clientChannel = null;
            AppInfo.isConnected = false;
            group.shutdownGracefully();
        }
    }
}
