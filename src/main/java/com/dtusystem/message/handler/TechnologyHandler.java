package com.dtusystem.message.handler;

import com.dtusystem.message.ExampleMessage;
import com.dtusystem.message.NetworkMsg;
import com.dtusystem.message.Technology;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TechnologyHandler extends AbstractMessageHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, NetworkMsg msg) {
        if (msg.getMessage() instanceof Technology) {
            log.debug("this is a technology");
            log.debug(msg.toString());
            ctx.writeAndFlush(new NetworkMsg(msg.getId(), new ExampleMessage(3, "this is a technology")));
            return;
        }
        if (next != null)
            next.handle(ctx, msg);
    }
}
