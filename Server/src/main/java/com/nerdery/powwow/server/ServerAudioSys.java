package com.nerdery.powwow.server;

import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioOutput;
import com.nerdery.powwow.net.PowWowNetwork;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class ServerAudioSys
implements AudioSys{
    private final PowWowNetwork network;

    @Inject
    private ServerAudioSys(PowWowNetwork network){
        this.network = network;
    }

    @Override
    public AudioInput input() {
        return null;
    }

    @Override
    public AudioOutput output() {
        return null;
    }

    @Override
    public byte[] buffer() {
        return new byte[0];
    }
}