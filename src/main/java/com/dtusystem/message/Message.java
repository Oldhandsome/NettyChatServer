package com.dtusystem.message;

import java.util.HashMap;
import java.util.Map;

public abstract class Message {

    public final static int HEART_BEAT = 1;
    public final static int FORMULA = 2;
    public final static int TECHNOLOGY = 3;
    public final static int EXAMPLE_MESSAGE = 4;
    public final static int LOGIN_REQUEST = 5;
    public final static int RESPONSE = 6;
    private final static Map<Integer, Class<? extends Message>> messageClassMapper = new HashMap<>();

    static {
        messageClassMapper.put(HEART_BEAT, HeartBeat.class);
        messageClassMapper.put(FORMULA, Formula.class);
        messageClassMapper.put(TECHNOLOGY, Technology.class);
        messageClassMapper.put(EXAMPLE_MESSAGE, ExampleMessage.class);
        messageClassMapper.put(LOGIN_REQUEST, LoginRequest.class);
        messageClassMapper.put(RESPONSE, Response.class);
    }

    public static Class<? extends Message> getMessageClass(int code) {
        return messageClassMapper.get(code);
    }

    public abstract int getMessageCode();
}
