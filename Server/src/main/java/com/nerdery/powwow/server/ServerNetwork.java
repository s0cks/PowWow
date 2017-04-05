package com.nerdery.powwow.server;

import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;
import com.nerdery.powwow.net.PowWowNetwork;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ServerNetwork
extends PowWowNetwork implements com.nerdery.powwow.net.ServerNetwork {
    private final Logger logger = LoggerFactory.getLogger(ServerNetwork.class);
    private final Injector injector;
    private ServerBootstrap bootstrap;

    @Inject
    private ServerNetwork(EventBus events, Injector injector) {
        super(events);
        this.injector = injector;
    }

    @Override
    public void connect() {
        if(this.getState() != State.DISCONNECTED) return;
        this.transition(State.STARTING);

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        this.bootstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(this.injector.getInstance(ServerChannelInitializer.class));
        try{
            this.bootstrap.bind(8080)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
            this.transition(State.CONNECTED);
        } catch(Exception e){
            this.logger.error("Couldn't start server", e);
            this.bootstrap = null;
            this.transition(State.DISCONNECTED);
        }
    }

    @Override
    public void disconnect() {

    }

    @Override
    public ServerBootstrap bootstrap(){
        return this.bootstrap;
    }
}