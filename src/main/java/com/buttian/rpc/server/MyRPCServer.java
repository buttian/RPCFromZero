package com.buttian.rpc.server;

import com.buttian.rpc.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyRPCServer {
    public static void main(String[] args){
        UserServiceImpl userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动");
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    try{
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        Integer id = objectInputStream.readInt();
                        User user = userService.getUserByUserId(id);
                        objectOutputStream.writeObject(user);
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("从IO中读取数据错误");
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
