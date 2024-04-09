package com.dtusystem.message;

public class HeartBeat extends Message {

    @Override
    public int getMessageCode() {
        return Message.HEART_BEAT;
    }
}
