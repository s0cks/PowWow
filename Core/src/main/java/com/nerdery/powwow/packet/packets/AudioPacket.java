package com.nerdery.powwow.packet.packets;

import com.nerdery.powwow.packet.PacketIdentifier;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPackets;
import io.netty.buffer.ByteBuf;

import javax.inject.Singleton;

@PacketIdentifier(PowWowPackets.AUDIO_PACKET)
public final class AudioPacket
extends PowWowPacket{
    public final String from;
    public final byte[] audio;

    public AudioPacket(String from, byte[] bytes){
        this.from = from;
        this.audio = bytes;
    }

    @Singleton
    public static final class Codec
    extends PowWowPacket.Codec<AudioPacket>{
        @Override
        public void encode(AudioPacket tPacket, ByteBuf buf) {
            this.writeUtf8(buf, tPacket.from);
            this.writeBytes(buf, tPacket.audio);
        }

        @Override
        public AudioPacket decode(ByteBuf buf) {
            return new AudioPacket(this.readUtf8(buf), this.readBytes(buf));
        }
    }
}