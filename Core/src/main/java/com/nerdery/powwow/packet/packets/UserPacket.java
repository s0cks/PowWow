package com.nerdery.powwow.packet.packets;

import com.nerdery.powwow.packet.PacketIdentifier;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPackets;
import io.netty.buffer.ByteBuf;

import javax.inject.Singleton;

@PacketIdentifier(PowWowPackets.USER_PACKET)
public final class UserPacket
extends PowWowPacket {
    public enum EventType{
        CONNECT,
        DISCONNECT,
    }

    public final EventType type;
    public final String user;

    private UserPacket(EventType type, String user){
        this.type = type;
        this.user = user;
    }

    public static UserPacket userConnect(String name){
        return new UserPacket(EventType.CONNECT, name);
    }

    public static UserPacket userDisconnect(String name){
        return new UserPacket(EventType.DISCONNECT, name);
    }

    @Singleton
    public static final class Codec
    extends PowWowPacket.Codec<UserPacket>{
        @Override
        public void encode(UserPacket tPacket, ByteBuf buf) {
            this.writeEnumValue(buf, tPacket.type);
            this.writeUtf8(buf, tPacket.user);
        }

        @Override
        public UserPacket decode(ByteBuf buf) {
            return new UserPacket(this.readEnum(buf, EventType.class), this.readUtf8(buf));
        }
    }
}