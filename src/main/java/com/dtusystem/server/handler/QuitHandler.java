package com.dtusystem.server.handler;

import com.dtusystem.server.service.Session;
import com.dtusystem.server.service.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    private final Session session = SessionFactory.getSession();

    // 连接断开
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        session.unBind(ctx.channel());
        log.debug("{} 已经关闭了！", ctx.channel());
    }

    // 当客户端强制关闭时，服务器抛出异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        session.unBind(ctx.channel());
        log.debug("{} 异常关闭了", ctx.channel());
    }

}
