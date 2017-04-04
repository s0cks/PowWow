package com.nerdery.powwow.client;

import com.google.common.eventbus.EventBus;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.nerdery.powwow.PowWowClient;
import com.nerdery.powwow.audio.*;
import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.inject.PowWowNames;
import com.nerdery.powwow.net.PowWowAudioClientNetwork;

import java.nio.ByteBuffer;

public final class PowWowClientModule
extends AudioModule{
    @Override
    protected void configure() {
        super.configure();
        this.bind(EventBus.class).annotatedWith(AudioSide.CLIENT.only()).toInstance(PowWowClient.events);
        this.bind(AudioInput.class).annotatedWith(AudioSide.CLIENT.only()).to(ClientAudioInput.class);
        this.bind(ByteBuffer.class).annotatedWith(Names.named(PowWowNames.AUDIO_SYS_INPUT_BUFFER)).toProvider(ClientAudioInput.class);
        this.bind(AudioSys.class).to(AudioClientSys.class);
        this.bind(AudioSide.class).toProvider(AudioClientSys.class);
        this.bind(PowWowAudioClientNetwork.class).to(AudioClientNetwork.class);
    }

    @Provides
    @AudioSideOnly(AudioSide.CLIENT)
    private Thread provideClientInputThread(){
        AudioInput input = PowWowClient.injector.getInstance(Key.get(AudioInput.class, AudioSide.CLIENT.only()));
        return new Thread(input, "[CLIENT] AudioInputThread");
    }
}