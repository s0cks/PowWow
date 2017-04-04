package com.nerdery.powwow.event;

import com.nerdery.powwow.inject.PowWowNames;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.ByteBuffer;

public final class AudioReadEvent{
    public final ByteBuffer buffer;

    @Inject
    private AudioReadEvent(@Named(PowWowNames.AUDIO_SYS_INPUT_BUFFER) ByteBuffer buffer){
        this.buffer = buffer;
    }
}