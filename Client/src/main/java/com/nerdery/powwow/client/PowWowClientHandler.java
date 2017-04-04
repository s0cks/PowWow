package com.nerdery.powwow.client;

import com.nerdery.powwow.PowWowClient;
import com.nerdery.powwow.packet.PowWowPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.inject.Singleton;

@Singleton
final class PowWowClientHandler
extends SimpleChannelInboundHandler<PowWowPacket>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PowWowPacket msg) throws Exception{
        PowWowClient.events.post(new PacketEvent(msg, ctx.channel()));
    }
}