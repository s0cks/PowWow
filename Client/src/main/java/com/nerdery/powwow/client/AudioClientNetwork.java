package com.nerdery.powwow.client;

import com.nerdery.powwow.net.PowWowAudioClientNetwork;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;

final class AudioClientNetwork
implements PowWowAudioClientNetwork{
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;
    private Bootstrap boot;

    @Override
    public void init() throws Exception {
        this.boot = new Bootstrap()
                .group(this.workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new AudioClientNetworkInitializer());
    }

    @Override
    public void start() throws Exception {
        this.channel = this.boot.connect(InetAddress.getLocalHost(), 8080)
                .sync()
                .channel();
    }

    @Override
    public Channel channel() {
        return this.channel;
    }
}