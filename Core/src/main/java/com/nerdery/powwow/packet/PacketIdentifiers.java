package com.nerdery.powwow.packet;

public final class PacketIdentifiers{
    private PacketIdentifiers(){}

    public static PacketIdentifier identifiedBy(int value){
        return new PacketIdentifierImpl(value);
    }
}