package com.buttian.rpc.codec;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {
    private Serializer serializer;

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        System.out.println(o.getClass());
        //消息类型（两个字节）
        if(o instanceof RPCRequest)
            byteBuf.writeShort(MessageType.REQUEST.getCode());
        else if(o instanceof RPCResponse)
            byteBuf.writeShort(MessageType.RESPONSE.getCode());
        //序列化方式（两个字节）
        byteBuf.writeShort(serializer.getType());
        //序列化编码
        byte[] serialize = serializer.serialize(o);
        //长度（四个字节）
        byteBuf.writeInt(serialize.length);
        //写入序列化后的data
        byteBuf.writeBytes(serialize);
    }
}
