package com.buttian.rpc.client;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;
import com.buttian.rpc.register.ServiceRegister;
import com.buttian.rpc.register.ZkServiceRegister;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRPCClient implements RPCClient{
    private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public SimpleRPCClient(){
        this.serviceRegister = new ZkServiceRegister();
    }
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();
        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream oos =new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse)ois.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
