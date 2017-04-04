package com.nerdery.powwow.audio;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public final class AudioSideOnlyImpl
implements AudioSideOnly, Serializable{
    private final AudioSide side;

    protected AudioSideOnlyImpl(AudioSide side){
        this.side = side;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AudioSideOnly)) return false;
        AudioSideOnly sideOnly = ((AudioSideOnly) obj);
        return sideOnly.value().equals(this.value());
    }

    @Override
    public int hashCode() {
        return (127 * "value".hashCode()) ^ this.side.hashCode();
    }

    @Override
    public AudioSide value() {
        return this.side;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return AudioSideOnly.class;
    }
}