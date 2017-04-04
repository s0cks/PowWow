package com.nerdery.powwow.client;

import com.google.inject.Inject;
import com.nerdery.powwow.audio.AudioSide;
import com.nerdery.powwow.audio.AudioSideOnly;
import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioOutput;

import javax.inject.Singleton;

@Singleton
final class AudioClientSys implements AudioSys {
    private final AudioInput input;

    @Inject
    private AudioClientSys(@AudioSideOnly(AudioSide.CLIENT) AudioInput input){
        this.input = input;
    }

    @Override
    public AudioInput input() {
        return this.input;
    }

    @Override
    public AudioOutput output() {
        return null;
    }

    @Override
    public AudioSide get() {
        return AudioSide.CLIENT;
    }
}