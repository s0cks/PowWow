package com.nerdery.powwow.packet;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.nerdery.powwow.packet.packets.AckPacket;
import com.nerdery.powwow.packet.packets.AudioPacket;
import com.nerdery.powwow.packet.packets.UserPacket;

public final class PowWowPacketModule
extends AbstractModule
implements PowWowPackets{
    @Override
    protected void configure() {
        this.bind(PowWowPacketProvider.class)
                .asEagerSingleton();

        this.registerCodec(ACK_PACKET, AckPacket.Codec.class);
        this.registerCodec(USER_PACKET, UserPacket.Codec.class);
        this.registerCodec(AUDIO_PACKET, AudioPacket.Codec.class);
    }

    private <T extends PowWowPacket> void registerCodec(int id, Class<? extends PowWowPacket.Codec<T>> codecClass){
        System.out.println("Bound: " + Key.get(PowWowPacket.Encoder.class, PacketIdentifiers.identifiedBy(id)) + " -> " + codecClass.getSimpleName());

        this.bind(PowWowPacket.Encoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
        this.bind(PowWowPacket.Decoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
    }
}