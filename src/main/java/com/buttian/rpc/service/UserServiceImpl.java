package com.buttian.rpc.service;

import com.buttian.rpc.common.User;
import com.buttian.rpc.service.UserService;

import java.util.Random;

public class UserServiceImpl implements UserService {

    @Override
    public User getUserByUserId(Integer id){
        System.out.println("客户端查询了id为：" + id + "的用户");
        //模拟从数据库中取数据
        User user = new User();
        user.setId(id);
        user.setUserName("shantaotao");
        boolean sex = new Random().nextBoolean();
        user.setSex(sex);
        return user;
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("插入数据成功：" + user);
        return 1;
    }

}
