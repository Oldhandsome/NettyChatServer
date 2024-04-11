package com.dtusystem.message.handler;

import com.dtusystem.message.ExampleMessage;
import com.dtusystem.message.Formula;
import com.dtusystem.message.NetworkMsg;
import com.dtusystem.message.Response;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FormulaHandler extends AbstractMessageHandler {

    @Override
    public void handle(ChannelHandlerContext ctx, NetworkMsg msg) {
        if (msg.getMessage() instanceof Formula) {
            log.debug("this is a formula");
            log.debug(msg.toString());
            ctx.writeAndFlush(new NetworkMsg(msg.getId(), new Response<>(true, "success", "this is a formula")));
            return;
        }
        if(next != null)
            next.handle(ctx, msg);
    }
}
