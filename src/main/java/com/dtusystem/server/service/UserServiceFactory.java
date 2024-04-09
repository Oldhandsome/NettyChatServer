package com.dtusystem.server.service;

public class UserServiceFactory {
    private final static UserService userService = new UserServiceMemoryImpl();

    public static UserService getUserService() {
        return userService;
    }
}
