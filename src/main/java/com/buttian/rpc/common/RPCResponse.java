package com.buttian.rpc.common;

import javafx.beans.binding.ObjectExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RPCResponse implements Serializable {
    //状态码
    private int code;
    private String message;
    //用其他序列化方式（如json）的时候得不到data的类型
    private Class<?> dataType;
    private Object data;
    public static RPCResponse success(Object data){
        //别忘记类型的初始化
        return RPCResponse.builder().code(200).dataType(data.getClass()).data(data).message("成功！").build();
    }
    public static RPCResponse fail(){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }
}
