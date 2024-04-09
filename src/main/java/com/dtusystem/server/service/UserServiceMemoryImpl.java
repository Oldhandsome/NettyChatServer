package com.dtusystem.server.service;

import java.util.HashMap;
import java.util.Map;

public class UserServiceMemoryImpl implements UserService{
    private final static Map<String, String> userWithPassword = new HashMap<>();

    {
        userWithPassword.put("zhangsan", "123");
        userWithPassword.put("lisi", "123");
        userWithPassword.put("wangwu", "123");
    }

    @Override
    public boolean login(String userName, String inputPassword) {
        String password = null;
        if((password = userWithPassword.get(userName)) != null){
            return password.equals(inputPassword);
        }
        return false;
    }
}
