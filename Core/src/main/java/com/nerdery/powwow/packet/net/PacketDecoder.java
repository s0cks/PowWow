package com.nerdery.powwow.packet.net;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nerdery.powwow.packet.PowWowPacketProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public final class PacketDecoder
extends ByteToMessageDecoder{
    private final Injector injector;
    private final PowWowPacketProvider packets;

    @Inject
    private PacketDecoder(Injector injector, PowWowPacketProvider packets){
        this.injector = injector;
        this.packets = packets;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(this.packets.decode(this.injector, in));
    }
}