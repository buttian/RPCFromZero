package com.buttian.rpc.client;

import com.buttian.rpc.common.Blog;
import com.buttian.rpc.common.User;
import com.buttian.rpc.service.BlogService;
import com.buttian.rpc.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class MyRPCClient {
    public static void main(String[] args){
        RPCClient nettyRPCClient = new NettyRPCClient("localhost", 8899);
        ClientProxy cp = new ClientProxy(nettyRPCClient);

        UserService userService = cp.getProxy(UserService.class);
        User userById = userService.getUserByUserId(new Random().nextInt());
        System.out.println("从服务端得到的user为：" + userById);

        User user = User.builder().userName("闪陶陶").id(new Random().nextInt()).sex(true).build();
        Integer integer = userService.insertUser(user);
        System.out.println("向服务端插入数据：" + integer);

        BlogService blogService = cp.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(new Random().nextInt());
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
