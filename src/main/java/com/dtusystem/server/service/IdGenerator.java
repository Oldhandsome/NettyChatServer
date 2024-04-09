package com.dtusystem.server.service;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {

    private IdGenerator(){}

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public static int getId() {
        if (atomicInteger.get() > 0)
            atomicInteger.set(0);
        return atomicInteger.decrementAndGet();
    }
}
