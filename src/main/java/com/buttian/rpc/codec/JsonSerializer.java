package com.buttian.rpc.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.buttian.rpc.common.RPCRequest;
import com.buttian.rpc.common.RPCResponse;

/**
 * json序列化方式把对象转化成字符串，丢失了data对象的类信息
 * deserializer从RPCResponse中获取对象的类信息，再转化
 */
public class JsonSerializer implements Serializer{
    public byte[] serialize(Object o) {
        byte[] bytes = JSONObject.toJSONBytes(o);
        return bytes;
    }

    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        //根据类型
        switch (messageType){
            case 0:
                //得到request对象
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                Object[] objects = new Object[request.getParams().length];
                for(int i = 0; i < objects.length; i++){
                    Class<?> paramType = request.getParasTypes()[i];
                    //如果参数是JsonObject 转化为JavaObject
                    if(!paramType.isAssignableFrom(request.getParams()[i].getClass()))
                        objects[i] = JSONObject.toJavaObject((JSONObject)request.getParams()[i], request.getParasTypes()[i]);
                    else
                        objects[i] = request.getParams()[i];
                }
                request.setParams(objects);
                obj = request;
                break;
            case 1:
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if(!dataType.isAssignableFrom(response.getData().getClass()))
                    response.setData(JSONObject.toJavaObject((JSONObject)response.getData(), dataType));
                obj = response;
                break;
            default:
                System.out.println("暂时不支持该种消息类型");
                throw new RuntimeException();
        }
        return obj;
    }

    public int getType() {
        return 1;
    }
}
