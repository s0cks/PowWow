package com.nerdery.powwow.audio;

import com.google.inject.AbstractModule;

import javax.sound.sampled.AudioFormat;

public abstract class AudioModule
extends AbstractModule{
    private final AudioFormat format = new AudioFormat(8000, 16, 1, true, true);

    @Override
    protected void configure() {
        this.bind(AudioFormat.class).toInstance(this.format);
    }
}