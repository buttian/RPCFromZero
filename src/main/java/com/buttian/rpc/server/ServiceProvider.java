package com.buttian.rpc.server;

import com.buttian.rpc.register.ServiceRegister;
import com.buttian.rpc.register.ZkServiceRegister;
import lombok.NoArgsConstructor;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ServiceProvider {
    private String host;
    private int port;
    private Map<String, Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    public ServiceProvider(String host, int port){
        this.port = port;
        this.host = host;
        this.serviceRegister = new ZkServiceRegister();
        this.interfaceProvider = new HashMap<>();
    }
    public void provideServiceInterface(Object service){
//        String interfaceName = service.getClass().getName();
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class clazz : interfaces) {
            interfaceProvider.put(clazz.getName(), service);
            serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }


}

