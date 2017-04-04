package com.nerdery.powwow.net;

import io.netty.channel.Channel;

public interface PowWowAudioClientNetwork{
    public void init() throws Exception;
    public void start() throws Exception;
    public Channel channel();
}