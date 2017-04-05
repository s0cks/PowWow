package com.nerdery.powwow.client;

import com.google.inject.Injector;
import com.nerdery.powwow.packet.net.PacketDecoder;
import com.nerdery.powwow.packet.net.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import javax.inject.Inject;

final class ClientChannelInitializer
extends ChannelInitializer<SocketChannel>{
    private final Injector injector;
    
    @Inject
    private ClientChannelInitializer(Injector injector){
        this.injector = injector;
    }
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(this.injector.getInstance(PacketDecoder.class))
                .addLast(this.injector.getInstance(PacketEncoder.class))
                .addLast(this.injector.getInstance(ClientChannelHandler.class));
    }
}