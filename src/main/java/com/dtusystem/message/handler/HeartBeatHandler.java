package com.dtusystem.message.handler;

import com.dtusystem.message.HeartBeat;
import com.dtusystem.message.NetworkMsg;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatHandler extends AbstractMessageHandler{
    private final NetworkMsg heartBeat = new NetworkMsg(0, new HeartBeat());

    @Override
    public void handle(ChannelHandlerContext ctx, NetworkMsg msg) {
        if (msg.getMessage() instanceof HeartBeat) {
            log.debug("this is a heart beat");
            heartBeat.setId(msg.getId());
            ctx.writeAndFlush(heartBeat);
            return;
        }
        if(next != null)
            next.handle(ctx, msg);
    }
}
