package com.example.demo.Netty;

import com.example.demo.Netty.codec.MessageCodec;
import com.example.demo.Netty.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Slf4j
@Component
public class Server {
    private static NioEventLoopGroup boss = new NioEventLoopGroup();
    private static NioEventLoopGroup worker = new NioEventLoopGroup();
    private static Properties properties;

    public ChannelFuture start() {
        properties = new Properties();
        ChannelFuture channelFuture = null;
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG,1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.channel(NioServerSocketChannel.class)
                    .group(boss, worker)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(16777216, 9, 4, 3, 0));
                            ch.pipeline().addLast(new MessageCodec());
                            ch.pipeline().addLast(new MyChannelHandler());
                            ch.pipeline().addLast(new RegisterRequestMessageHandler());
                            ch.pipeline().addLast(new LoginRequestHandler());
                            ch.pipeline().addLast(new ChatRequestMessageHandler());
                            ch.pipeline().addLast(new ChatRecordRequestMessageHandler());
                            ch.pipeline().addLast(new MessageHasReadRequestHandler());
                            ch.pipeline().addLast(new DataRequestHandler());
                        }
                    });
            channelFuture = bootstrap.bind("172.20.10.10",7000).sync();
            //channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channelFuture;
    }

    public void close() {
        boss.shutdownGracefully();
        worker.shutdownGracefully();
    }

}

