package com.buttian.rpc.client;

import com.buttian.rpc.common.User;
import com.buttian.rpc.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class MyRPCClient {
    public static void main(String[] args){
        ClientProxy cp = new ClientProxy("localhost", 8899);
        UserService proxy = cp.getProxy(UserService.class);

        User userById = proxy.getUserByUserId(new Random().nextInt());
        System.out.println("从服务端得到的user为：" + userById);

        User user = User.builder().userName("闪陶陶").id(new Random().nextInt()).sex(true).build();
        Integer integer = proxy.insertUser(user);
        System.out.println("向服务端插入数据：" + integer);
    }
}
