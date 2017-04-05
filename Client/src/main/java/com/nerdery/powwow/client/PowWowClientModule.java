package com.nerdery.powwow.client;

import com.google.common.eventbus.EventBus;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import com.nerdery.powwow.PowWowClient;
import com.nerdery.powwow.PowWowNames;
import com.nerdery.powwow.PowWowState;
import com.nerdery.powwow.audio.AudioModule;
import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.side.PowWowSide;
import com.nerdery.powwow.side.PowWowSideOnly;
import com.nerdery.powwow.util.AtomicReferenceProvider;

public final class PowWowClientModule
extends AudioModule{
    public PowWowClientModule(){
        super(PowWowSide.LOCAL);
    }

    @Override
    protected void configure() {
        super.configure();
        this.bind(AudioInput.class).to(AudioInputJavaSound.class);
        this.bind(PowWowNetwork.class).to(ClientNetwork.class);
        this.bind(AudioSys.class).to(ClientAudioSys.class);
        this.bind(String.class).annotatedWith(Names.named(PowWowNames.CLIENT_USER_NAME)).toProvider(new AtomicReferenceProvider<>(PowWowClient.userRef));
        this.bind(PowWowState.class).toProvider(new AtomicReferenceProvider<>(PowWowClient.stateRef));
        this.bindInterceptor(Matchers.any(), Matchers.annotatedWith(PowWowSideOnly.class), this.sideOnlyInterceptor);
    }

    @Provides
    public EventBus provideEventBus(){
        return PowWowClient.events;
    }
}