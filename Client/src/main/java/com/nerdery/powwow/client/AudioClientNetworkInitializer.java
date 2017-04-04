package com.nerdery.powwow.client;

import com.nerdery.powwow.PowWowClient;
import com.nerdery.powwow.packet.PacketDecoder;
import com.nerdery.powwow.packet.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

final class AudioClientNetworkInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(PowWowClient.injector.getInstance(PacketDecoder.class))
                .addLast(PowWowClient.injector.getInstance(PacketEncoder.class))
                .addLast(PowWowClient.injector.getInstance(PowWowClientHandler.class));
    }
}