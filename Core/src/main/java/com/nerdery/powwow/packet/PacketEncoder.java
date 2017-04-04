package com.nerdery.powwow.packet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class PacketEncoder
extends MessageToByteEncoder<PowWowPacket>{
    private final PowWowPacketProvider packets;
    private final Injector injector;

    @Inject
    private PacketEncoder(PowWowPacketProvider packets, Injector injector){
        this.packets = packets;
        this.injector = injector;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, PowWowPacket msg, ByteBuf out) throws Exception {
        this.packets.encode(this.injector, out, msg);
    }
}