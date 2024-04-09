package com.dtusystem.server.service;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMemoryImpl implements Session{
    private final Map<String, Channel> usernameChannelMap = new ConcurrentHashMap<>();
    private final Map<Channel, String> channelUserNameMap = new ConcurrentHashMap<>();

    @Override
    public void bind(Channel channel, String username) {
        usernameChannelMap.put(username, channel);
        channelUserNameMap.put(channel, username);
    }

    @Override
    public void unBind(Channel channel) {
        String username = channelUserNameMap.remove(channel);
        usernameChannelMap.remove(username);
    }

    @Override
    public Channel getChannel(String username) {
        return usernameChannelMap.get(username);
    }

    @Override
    public String getUsername(Channel channel) {
        return channelUserNameMap.get(channel);
    }

    @Override
    public String toString() {
        return usernameChannelMap.toString();
    }
}
