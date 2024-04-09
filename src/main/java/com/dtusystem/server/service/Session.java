package com.dtusystem.server.service;

import io.netty.channel.Channel;

// 将channel和用户名进行绑定
public interface Session {
    void bind(Channel channel, String username);

    void unBind(Channel channel);

    Channel getChannel(String userName);

    String getUsername(Channel channel);

}
