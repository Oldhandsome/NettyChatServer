package com.dtusystem.server.handler;

import com.dtusystem.message.Message;
import com.dtusystem.message.NetworkMsg;
import com.dtusystem.message.Response;
import com.dtusystem.server.service.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class SessionHandler extends SimpleChannelInboundHandler<NetworkMsg> {

    private final List<Integer> messageCodes = List.of(
            Message.HEART_BEAT,
            Message.LOGIN_REQUEST
    );

    private final Session session = SessionFactory.getSession();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetworkMsg msg) {
        System.out.println(msg.getMessage().toString());
        String username = session.getUsername(ctx.channel());
        if (verify(msg.getMessage()) && username == null) {
            NetworkMsg networkMsg = new NetworkMsg(msg.getId(), new Response(false, "请登录"));
            ctx.write(networkMsg);
            return;
        }
        ctx.fireChannelRead(msg);
    }

    private boolean verify(Message message) {
        int messageCode = message.getMessageCode();
        return !messageCodes.contains(messageCode);
    }
}
