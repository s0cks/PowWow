package com.nerdery.powwow.packet.net;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPacketProvider;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PacketEncoder
extends MessageToByteEncoder<PowWowPacket>{
    private final Logger logger = LoggerFactory.getLogger(PacketEncoder.class);
    private final PowWowPacketProvider packets;
    private final Injector injector;

    @Inject
    private PacketEncoder(PowWowPacketProvider packets, Injector injector){
        this.packets = packets;
        this.injector = injector;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, PowWowPacket msg, ByteBuf out) throws Exception {
        this.logger.info("Encoding packet: " + msg.getClass().getSimpleName());
        this.packets.encode(this.injector, out, msg);
    }
}