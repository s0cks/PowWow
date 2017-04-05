package com.nerdery.powwow.client;

import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioOutput;
import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.net.PowWowNetwork;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ClientAudioSys
implements AudioSys{
    private final PowWowNetwork network;
    private final AudioInput input;

    @Inject
    private ClientAudioSys(PowWowNetwork network, AudioInput input){
        this.network = network;
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
    public byte[] buffer() {
        return new byte[128000];
    }
}