package com.nerdery.powwow.packet.packets;

import com.nerdery.powwow.packet.PacketIdentifier;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPackets;
import io.netty.buffer.ByteBuf;

import javax.inject.Singleton;

@PacketIdentifier(PowWowPackets.AUDIO_EVENT_PACKET)
public final class AudioEventPacket
extends PowWowPacket{
    public enum EventType{
        SEND,
        RECEIVE;
    }

    public final EventType type;
    public final String user;
    public final byte[] audio;

    private AudioEventPacket(EventType type, String user, byte[] audio){
        this.type = type;
        this.user = user;
        this.audio = audio;
    }

    public static AudioEventPacket audioSend(String user, byte[] bytes){
        return new AudioEventPacket(EventType.SEND, user, bytes);
    }

    @Singleton
    public static final class Codec
    extends PowWowPacket.Codec<AudioEventPacket>{
        @Override
        public void encode(AudioEventPacket tPacket, ByteBuf buf) {
            this.writeEnumValue(buf, tPacket.type);
            this.writeUtf8(buf, tPacket.user);
            this.writeBytes(buf, tPacket.audio);
        }

        @Override
        public AudioEventPacket decode(ByteBuf buf) {
            EventType type = this.readEnum(buf, EventType.class);
            return new AudioEventPacket(type, this.readUtf8(buf), this.readBytes(buf));
        }
    }
}