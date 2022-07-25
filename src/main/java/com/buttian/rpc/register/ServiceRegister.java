package com.buttian.rpc.register;

import java.net.InetSocketAddress;

public interface ServiceRegister {
    //注册：保存服务与地址
    void register(String serviceName, InetSocketAddress serverAddress);
    //根据serviceName查找地址
    InetSocketAddress serviceDiscovery(String serviceName);
}
