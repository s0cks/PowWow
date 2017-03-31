package com.nerdery.powwow.packet;

import java.lang.annotation.Annotation;

final class PacketIdentifierImpl
implements PacketIdentifier{
    private final int id;

    protected PacketIdentifierImpl(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PacketIdentifier)) return false;
        PacketIdentifier ident = ((PacketIdentifier) obj);
        return ident.value() == this.value();
    }

    @Override
    public int hashCode() {
        return (127 * "value".hashCode()) ^ this.value();
    }

    @Override
    public int value() {
        return this.id;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return PacketIdentifier.class;
    }

    @Override
    public String toString() {
        return "PacketIdentifier{" +
                "id=" + id +
                '}';
    }
}