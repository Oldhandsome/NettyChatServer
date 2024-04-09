package com.dtusystem;

import com.dtusystem.protocol.MessageCodecSharable;
import com.dtusystem.protocol.ProtocolLengthFieldDecoder;
import com.dtusystem.server.handler.MessageHandler;
import com.dtusystem.server.handler.QuitHandler;
import com.dtusystem.server.handler.SessionHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        MessageHandler MESSAGE_HANDLER = new MessageHandler();
        SessionHandler SESSION_HANDLER = new SessionHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss, worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            // 为读事件定义超时处理器，在读写超时时，触发下面的userEventTriggered函数
                            .addLast(new IdleStateHandler(9, 0, 0))
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            .addLast(new ChannelDuplexHandler() {
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                    if (evt instanceof IdleStateEvent) {
                                        IdleStateEvent state = (IdleStateEvent) evt;
                                        if (state.state() == IdleState.READER_IDLE) {
                                            log.debug("{} 已经9秒没有读取到数据了！", ctx.channel());
                                            ctx.channel().close();
                                        }
                                    }
                                }
                            })
                            .addLast(new ProtocolLengthFieldDecoder())
                            .addLast(LOGGING_HANDLER)
                            .addLast(MESSAGE_CODEC)
                            .addLast(SESSION_HANDLER)
                            .addLast(MESSAGE_HANDLER)
                            .addLast(QUIT_HANDLER);
                }
            });
            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
