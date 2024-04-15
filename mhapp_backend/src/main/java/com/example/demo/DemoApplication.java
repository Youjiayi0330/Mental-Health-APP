package com.example.demo;

import com.example.demo.Netty.Server;
import io.netty.channel.ChannelFuture;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Resource
    private Server server;

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture channelFuture = server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                server.close();
            }
        });
        channelFuture.channel().closeFuture().sync();
    }
}
