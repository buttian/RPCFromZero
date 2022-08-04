package com.buttian.rpc.client;

import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    //不同的service需要进行不同的封装，需要一层动态代理根据反射封装不同的Service
    //传入参数Service接口的class对象，反射封装成一个request

    private RPCClient client;

    //jdk动态代理
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .parasTypes(method.getParameterTypes()).build();
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }

}
