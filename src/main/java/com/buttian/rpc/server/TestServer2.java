package com.buttian.rpc.server;

import com.buttian.rpc.service.BlogService;
import com.buttian.rpc.service.BlogServiceImpl;
import com.buttian.rpc.service.UserService;
import com.buttian.rpc.service.UserServiceImpl;

public class TestServer2 {
    public static void main(String[] args){
        BlogService blogService = new BlogServiceImpl();
        UserService userService = new UserServiceImpl();
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8900);
        serviceProvider.provideServiceInterface(blogService);
        serviceProvider.provideServiceInterface(userService);

        RPCServer nettyRPCServer = new NettyRPCServer(serviceProvider);
        nettyRPCServer.start(8900);
    }
}
