package com.nerdery.powwow.server;

import com.google.common.eventbus.EventBus;
import com.nerdery.powwow.packet.PowWowPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ServerChannelHandler
extends SimpleChannelInboundHandler<PowWowPacket>{
    private final Logger logger = LoggerFactory.getLogger(ServerChannelHandler.class);
    private final EventBus events;

    @Inject
    private ServerChannelHandler(EventBus bus){
        this.events = bus;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PowWowPacket msg) throws Exception {
        this.logger.info("Reading packet {}", msg.getClass().getSimpleName());
    }
}