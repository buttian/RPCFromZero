package com.buttian.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class MyDecode extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //读取消息类型
        short messageType = byteBuf.readShort();
        if(messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            System.out.println("暂不支持此种消息类型");
            return;
        }
        //读取序列化类型，并得到对应的序列化器
        short serializerType = byteBuf.readShort();
        Serializer serializer = Serializer.getSerializerByCode(serializerType);
        if(serializer == null)
            throw new RuntimeException("不存在对应的序列化器");
        //消息长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        //读取序列化数组
        byteBuf.readBytes(bytes);
        //解码
        Object data = serializer.deserialize(bytes, messageType);
        list.add(data);
    }
}
