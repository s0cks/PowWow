package com.nerdery.powwow.data;

import com.nerdery.powwow.packet.PowWowPacket;
import io.netty.channel.Channel;

public final class User{
    public final Channel channel;

    public User(Channel channel){
        this.channel = channel;
    }

    public void send(PowWowPacket packet){
        this.channel.writeAndFlush(packet);
    }
}