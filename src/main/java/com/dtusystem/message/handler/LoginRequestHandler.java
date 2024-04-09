package com.dtusystem.message.handler;

import com.dtusystem.message.LoginRequest;
import com.dtusystem.message.NetworkMsg;
import com.dtusystem.message.Response;
import com.dtusystem.server.service.Session;
import com.dtusystem.server.service.SessionFactory;
import com.dtusystem.server.service.UserService;
import com.dtusystem.server.service.UserServiceFactory;
import io.netty.channel.ChannelHandlerContext;

public class LoginRequestHandler extends AbstractMessageHandler {

    private final UserService userService = UserServiceFactory.getUserService();

    private final Session session = SessionFactory.getSession();

    @Override
    public void handle(ChannelHandlerContext ctx, NetworkMsg msg) {
        if (msg.getMessage() instanceof LoginRequest) {
            LoginRequest request = (LoginRequest) msg.getMessage();
            boolean flag = userService.login(request.getUsername(), request.getPassword());
            if (flag) {
                session.bind(ctx.channel(), request.getUsername());
                Response response = new Response(true, request.getUsername() + " 用户登录成功！", null);
                ctx.writeAndFlush(new NetworkMsg(msg.getId(), response));
            } else {
                Response response = new Response(false, "用户或密码错误！！！", null);
                ctx.writeAndFlush(new NetworkMsg(msg.getId(), response));
            }
            return;
        }
        if (next != null)
            next.handle(ctx, msg);
    }
}
