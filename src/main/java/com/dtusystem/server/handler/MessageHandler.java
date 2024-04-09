package com.dtusystem.server.handler;


import com.dtusystem.message.*;
import com.dtusystem.message.handler.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NetworkMsg> {
    private final AbstractMessageHandler messageHandlerChain;

    {
        HeartBeatHandler heartBeatHandler = new HeartBeatHandler();
        FormulaHandler formulaHandler = new FormulaHandler();
        TechnologyHandler technologyHandler = new TechnologyHandler();
        LoginRequestHandler loginRequestHandler = new LoginRequestHandler();

        heartBeatHandler.setNextMessageHandler(formulaHandler);
        formulaHandler.setNextMessageHandler(technologyHandler);
        technologyHandler.setNextMessageHandler(loginRequestHandler);

        messageHandlerChain = heartBeatHandler;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NetworkMsg msg) throws Exception {
        messageHandlerChain.handle(ctx, msg);
    }
}
