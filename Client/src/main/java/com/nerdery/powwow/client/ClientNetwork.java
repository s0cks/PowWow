package com.nerdery.powwow.client;

import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.packet.PowWowPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Singleton
final class ClientNetwork
extends PowWowNetwork implements com.nerdery.powwow.net.ClientNetwork {
    private final Logger logger = LoggerFactory.getLogger(ClientNetwork.class);
    private final Injector injector;
    private Bootstrap boostrap;
    private Channel channel;

    @Inject
    private ClientNetwork(EventBus bus, Injector injector){
        super(bus);
        this.injector = injector;
    }

    @Override
    public void connect() {
        if(this.getState() != State.DISCONNECTED) return;
        this.transition(State.STARTING);

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        this.boostrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(this.injector.getInstance(ClientChannelInitializer.class));

        try {
            this.channel = this.boostrap.connect(InetAddress.getLocalHost(), 8080)
                    .sync()
                    .channel();
            this.transition(State.CONNECTED);
        } catch (InterruptedException | UnknownHostException e){
            this.logger.error("Cannot connect to server", e);
            this.channel = null;
            this.boostrap = null;
            this.transition(State.DISCONNECTED);
        }
    }

    @Override
    public void disconnect() {
        try{
            this.transition(State.STOPPING);
            this.channel.closeFuture().sync();
        } catch (InterruptedException e){
            this.logger.error("Couldn't disconnect from server", e);
        }

        this.channel = null;
        this.boostrap = null;
        this.transition(State.DISCONNECTED);
    }

    @Override
    public Bootstrap bootstrap() {
        return this.boostrap;
    }

    @Override
    public void send(PowWowPacket packet) {
        this.channel().writeAndFlush(packet);
    }

    @Override
    public Channel channel() {
        return this.channel;
    }
}