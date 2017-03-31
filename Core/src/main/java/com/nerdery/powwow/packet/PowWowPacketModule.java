package com.nerdery.powwow.packet;

import com.google.inject.AbstractModule;

public final class PowWowPacketModule
extends AbstractModule
implements PowWowPackets{
    @Override
    protected void configure() {
        this.bind(PowWowPacketProvider.class)
                .asEagerSingleton();
        this.registerCodec(USER_EVENT_PACKET, UserEventPacket.Codec.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends PowWowPacket> void registerCodec(int id, Class<? extends PowWowPacket.Codec<T>> codecClass){
        this.bind(PowWowPacket.Encoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
        this.bind(PowWowPacket.Decoder.class).annotatedWith(PacketIdentifiers.identifiedBy(id)).to(codecClass);
    }
}