package com.nerdery.powwow.packet;

import com.google.inject.AbstractModule;
import com.nerdery.powwow.packet.packets.AudioEventPacket;
import com.nerdery.powwow.packet.packets.UserEventPacket;

public final class PowWowPacketModule
extends AbstractModule
implements PowWowPackets{
    @Override
    protected void configure() {
        this.bind(PowWowPacketProvider.class)
                .asEagerSingleton();
        this.registerCodec(USER_EVENT_PACKET, UserEventPacket.Codec.class);
        this.registerCodec(AUDIO_EVENT_PACKET, AudioEventPacket.Codec.class);
    }

    private <T extends PowWowPacket> void registerCodec(int id, Class<? extends PowWowPacket.Codec<T>> codecClass){
        this.bind(PowWowPacket.Encoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
        this.bind(PowWowPacket.Decoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
    }
}