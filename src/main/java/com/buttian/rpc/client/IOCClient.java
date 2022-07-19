package com.buttian.rpc.client;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOCClient {
    //底层与服务端的通信
    //这里的request是上层封装好的
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) throws IOException, ClassNotFoundException {

        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println("request:" + request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            return response;
        }catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }


    }

}
