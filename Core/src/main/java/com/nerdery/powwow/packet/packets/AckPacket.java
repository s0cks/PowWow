package com.nerdery.powwow.packet.packets;

import com.nerdery.powwow.packet.PacketIdentifier;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.PowWowPackets;
import io.netty.buffer.ByteBuf;

import javax.inject.Singleton;

@PacketIdentifier(PowWowPackets.ACK_PACKET)
public final class AckPacket
extends PowWowPacket{
    public enum Status{
        SUCCESS,
        FAILURE;
    }

    public final Status status;

    private AckPacket(Status status){
        this.status = status;
    }

    public static AckPacket success(){
        return new AckPacket(Status.SUCCESS);
    }

    public static AckPacket failure(){
        return new AckPacket(Status.FAILURE);
    }

    @Singleton
    public static final class Codec
    extends PowWowPacket.Codec<AckPacket>{
        @Override
        public void encode(AckPacket tPacket, ByteBuf buf) {
            this.writeEnumValue(buf, tPacket.status);
        }

        @Override
        public AckPacket decode(ByteBuf buf) {
            return new AckPacket(this.readEnum(buf, Status.class));
        }
    }
}