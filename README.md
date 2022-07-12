# RPCFromZero
从0开始，手写一个RPC

####Version0

服务端与客户端通过socket通信

客户端建立Socket连接，传输Id给服务端，得到返回的User对象

服务端以BIO的方式监听Socket，如有数据，调用对应服务的实现类执行任务，将结果返回给客户端

####Version1

1.定义通用的消息格式RPCRequest和RPCResponse

2.客户端动态代理封装request

    RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .params(args).paramsTypes(method.getParameterTypes()).build();

3.服务端反射调用对应方法

    Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
    Object invoke = method.invoke(userService, request.getParams());



