package com.buttian.rpc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * java原始的BIO监听模式，来一个任务，就new一个线程处理
 * 具体处理任务工作从服务端分离
 */
public class SimpleRPCServer implements RPCServer{

    private ServiceProvider serviceProvider;
    public SimpleRPCServer(ServiceProvider serviceProvider){
        this.serviceProvider = serviceProvider;
    }


    @Override
    public void start(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动");
            while(true){
                Socket socket = serverSocket.accept();
                //开启一个线程去处理
                new Thread(new WorkThread(socket, serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务端启动失败");
        }


    }

    @Override
    public void stop() {

    }
}
