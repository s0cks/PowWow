package com.nerdery.powwow.packet;

import com.google.inject.Injector;
import com.google.inject.Key;
import io.netty.buffer.ByteBuf;

public final class PowWowPacketProvider{
    @SuppressWarnings("unchecked")
    public <T extends PowWowPacket> T decode(Injector injector, ByteBuf buf){
        Key decoderKey = Key.get(PowWowPacket.Decoder.class, PacketIdentifiers.identifiedBy(buf.readInt()));
        PowWowPacket.Decoder<T> decoder = ((PowWowPacket.Decoder<T>) injector.getInstance(decoderKey));
        return decoder.decode(buf);
    }

    @SuppressWarnings("unchecked")
    public <T extends PowWowPacket> void encode(Injector injector, ByteBuf buf, T packet){
        int ident;
        try{
            PacketIdentifier identifier = packet.getClass().getDeclaredAnnotation(PacketIdentifier.class);
            ident = identifier.value();
        } catch(Exception e){
            throw new IllegalStateException("Invalid packet: " + packet.getClass().getSimpleName());
        }

        Key encoderKey = Key.get(PowWowPacket.Encoder.class, PacketIdentifiers.identifiedBy(ident));
        PowWowPacket.Encoder<T> encoder = ((PowWowPacket.Encoder<T>) injector.getInstance(encoderKey));
        buf.writeInt(ident);
        encoder.encode(packet, buf);
    }
}