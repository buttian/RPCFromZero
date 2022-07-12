package com.buttian.rpc.server;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class MyRPCServer {
    public static void main(String[] args){
        UserServiceImpl userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动");
            //BIO的方式监听Socket
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    try{
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        //读取客户端传来的对象
                        RPCRequest request = (RPCRequest) ois.readObject();
                        //反射
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParasTypes());
                        Object invoke = method.invoke(userService,request.getParams());
                        //封装
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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
