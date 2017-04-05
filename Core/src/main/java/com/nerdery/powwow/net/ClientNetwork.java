package com.nerdery.powwow.net;

import com.nerdery.powwow.packet.PowWowPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

public interface ClientNetwork{
    public Channel channel();
    public Bootstrap bootstrap();
    public void send(PowWowPacket packet);
}