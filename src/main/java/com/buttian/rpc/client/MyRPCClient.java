package com.buttian.rpc.client;

import com.buttian.rpc.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class MyRPCClient {
    public static void main(String[] args){
        try{
            //建立socket连接
            Socket socket = new Socket("localhost", 8899);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //传给服务端id
            objectOutputStream.writeInt(new Random().nextInt());
            objectOutputStream.flush();
            //从服务端返回的user
            Object o = objectInputStream.readObject();
            User user = (User)o;
            System.out.println("服务端返回user：" + user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }
    }
}
