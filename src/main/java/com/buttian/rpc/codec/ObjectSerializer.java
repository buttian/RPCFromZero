package com.buttian.rpc.codec;

import java.io.*;

public class ObjectSerializer implements Serializer{
    //java io对象 2 字节数组
    public byte[] serialize(Object o) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //字节数组 2 对象
    //messageType参数给Json序列化方式用
    public Object deserialize(byte[] bytes, int messageType) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try{
            ObjectInputStream ois = new ObjectInputStream(bis);
            o = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public int getType() {
        return 0;
    }
}
