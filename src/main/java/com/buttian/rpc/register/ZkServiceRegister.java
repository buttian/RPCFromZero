package com.buttian.rpc.register;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;


public class ZkServiceRegister implements ServiceRegister{
    private CuratorFramework client;
    private static final String ROOT_PATH = "MyRPC";
    //zookeeper客户端初始化，与zookeeper服务端建立连接
    public ZkServiceRegister(){
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        this.client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(40000)
                .retryPolicy(policy)
                .namespace(ROOT_PATH)
                .build();
        this.client.start();
        System.out.println("zookeeper连接成功");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try{
            //serviceName创建成永久节点
            if(client.checkExists().forPath("/" + serviceName) == null){
                client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath("/" + serviceName);
            }
            String path = "/" + serviceName + "/" + getServiceAddress(serverAddress);
            //创建临时节点
            client.create().creatingParentContainersIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try{
            //获取子节点
            List<String> strings = client.getChildren().forPath("/" + serviceName);
            String address = strings.get(0);
            return parseAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //地址 2 xxx.xxx.xxx.xxx:port
    private String getServiceAddress(InetSocketAddress serverAddress){
        return serverAddress.getHostString() + ":" + serverAddress.getPort();
    }

    //字符串 2 地址
    private InetSocketAddress parseAddress(String s){
        String[] results = s.split(":");
        return new InetSocketAddress(results[0], Integer.parseInt(results[1]));
    }
}
