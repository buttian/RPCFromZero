package com.buttian.rpc.client;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;
import com.buttian.rpc.register.ServiceRegister;
import com.buttian.rpc.register.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;


public class NettyRPCClient implements RPCClient{
    private String host;
    private int port;
    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private ServiceRegister serviceRegister;
    public NettyRPCClient(){
        this.serviceRegister = new ZkServiceRegister();
    }
    //netty客服端初始化，重复使用
    static{
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();
        try{
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //发送数据
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(request);
            //主线程wait阻塞
            //因为netty的传输都是异步的，你发送request，会立刻返回(本例中会返回空指针)， 而不是想要的相应的response
            channel.closeFuture().sync();
            //从AttributeMap中取
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();

            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
