package com.nerdery.powwow.packet.packets;

import com.nerdery.powwow.packet.PacketIdentifier;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPackets;
import io.netty.buffer.ByteBuf;

import javax.inject.Singleton;

@PacketIdentifier(PowWowPackets.USER_EVENT_PACKET)
public final class UserEventPacket
extends PowWowPacket {
    public enum EventType{
        CONNECT,
        DISCONNECT;
    }

    public final EventType type;
    public final String user;

    private UserEventPacket(EventType type, String user){
        this.type = type;
        this.user = user;
    }

    public static UserEventPacket userConnect(String name){
        return new UserEventPacket(EventType.CONNECT, name);
    }

    public static UserEventPacket userDisconnect(String name){
        return new UserEventPacket(EventType.DISCONNECT, name);
    }

    @Singleton
    public static final class Codec
    extends PowWowPacket.Codec<UserEventPacket>{
        @Override
        public void encode(UserEventPacket tPacket, ByteBuf buf) {
            this.writeEnumValue(buf, tPacket.type);
            this.writeUtf8(buf, tPacket.user);
        }

        @Override
        public UserEventPacket decode(ByteBuf buf) {
            return new UserEventPacket(this.readEnum(buf, EventType.class), this.readUtf8(buf));
        }
    }
}