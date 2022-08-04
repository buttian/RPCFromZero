package com.buttian.rpc.codec;

//序列化器的接口
public interface Serializer {
    //把对象序列化成字节数组
    byte[] serialize(Object o);
    //指定消息格式，根据message转化成相应的对象
    Object deserialize(byte[] bytes, int messageType);
    //代表使用的序列器
    //0：java自带 1：json序列化格式
    int getType();
    //根据序号取出序列化器
    static Serializer getSerializerByCode(int code){
        switch (code){
            case 0 :
                return new ObjectSerializer();
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }


}
