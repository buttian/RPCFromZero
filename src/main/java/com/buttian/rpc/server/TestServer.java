package com.buttian.rpc.server;

import com.buttian.rpc.service.BlogService;
import com.buttian.rpc.service.BlogServiceImpl;
import com.buttian.rpc.service.UserService;
import com.buttian.rpc.service.UserServiceImpl;

public class TestServer {
    public static void main(String[] args){
        BlogService blogService = new BlogServiceImpl();
        UserService userService = new UserServiceImpl();
//        HashMap<String, Object> serviceProvide = new HashMap<>();
//        serviceProvide.put("com.buttian.rpc.service.BlogService", blogService);
//        serviceProvide.put("com.buttian.rpc.service.UserService", userService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(blogService);
        serviceProvider.provideServiceInterface(userService);

//        RPCServer rpcserver = new SimpleRPCServer(serviceProvider);
//        rpcserver.start(8899);

//        RPCServer threadPoolRpcServer = new ThreadPoolRPCServer(serviceProvider);
//        threadPoolRpcServer.start(8899);

        RPCServer nettyRPCServer = new NettyRPCServer(serviceProvider);
        nettyRPCServer.start(8899);
    }

}
