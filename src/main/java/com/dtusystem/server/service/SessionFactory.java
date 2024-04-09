package com.dtusystem.server.service;

public class SessionFactory {
    private final static SessionMemoryImpl session = new SessionMemoryImpl();

    public static Session getSession() {
        return session;
    }
}
