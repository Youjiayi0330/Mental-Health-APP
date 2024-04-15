package com.example.demo.Netty.codec;

import com.example.demo.Netty.message.MessageType;
import com.example.demo.Netty.message.MyMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ChannelHandler.Sharable
@Slf4j
public class MessageCodec extends MessageToMessageCodec<ByteBuf, MyMessage> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage message, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        //魔数LXC
        out.writeBytes(new byte[]{'L', 'X', 'C'});
        //版本号
        out.writeByte(1);
        //消息类型
        out.writeByte(message.getMessageType());
        //消息序号，用于提供双工通信
        out.writeInt(message.getSequenceId());
        //消息对象转为json字符串，并获取消息的长度
//        Gson gson = new GsonBuilder().
//                setDateFormat("yyyy-MM-dd HH:mm:ss").
//                registerTypeAdapter(Class.class, new ClassCodec()).
//                create();
//        String json = gson.toJson(message);
        String json = mapper.writeValueAsString(message);
        int length = json.getBytes().length;
        //写入长度
        out.writeInt(length);
        //填充字段
        out.writeByte(0xff);
        out.writeByte(0xff);
        out.writeByte(0xff);


        //写入正文内容
        out.writeBytes(json.getBytes());
        System.out.println(message);
        System.out.println(json);
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        //取出3字节魔数
        byte[] magicNum = new byte[3];
        in.readBytes(magicNum);
        //取出版本号
        byte version = in.readByte();
        //消息类型
        byte messageType = in.readByte();
        //消息序号
        int sequenceId = in.readInt();
        //正文长度
        int len = in.readInt();
        //消耗掉填充字段
        in.readByte();
        in.readByte();
        in.readByte();
        //读取到正文
        byte[] message = new byte[len];
        in.readBytes(message, 0, len);
        //反序列化
        String json = new String(message);
//        Gson gson = new GsonBuilder().
//                setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .registerTypeAdapter(Class.class, new ClassCodec())
//                .create();
        //此类型不能为父类型，否则报错
//        MyMessage myMessage = gson.fromJson(json, MessageType.getMessageClass(messageType));
        MyMessage myMessage = mapper.readValue(json, MessageType.getMessageClass(messageType));

        log.debug("{}  {}  {}  {}  {} {}", new String(magicNum), version, messageType, sequenceId, len, myMessage);
        list.add(myMessage);
    }
}
