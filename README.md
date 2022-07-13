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

####Version2

1.服务端提供不同服务，用Map<String, Object>保存接口名和对应的实现类；在ServiceProvider中完成相关操作，利用class对象自动得到接口名

2.服务端代码的解耦

①抽象RPCServer接口

②将解析request请求，执行方法，封装response的过程从服务端分类出来（WorkThread）

3.进一步实现线程池版服务端，提高性能






