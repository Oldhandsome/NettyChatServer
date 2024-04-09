package com.dtusystem.message.handler;

import com.dtusystem.message.NetworkMsg;
import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractMessageHandler {

    protected AbstractMessageHandler next;

    public void setNextMessageHandler(AbstractMessageHandler next) {
        this.next = next;
    }

    public abstract void handle(ChannelHandlerContext ctx, NetworkMsg msg);

}
