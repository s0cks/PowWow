package com.nerdery.powwow.server;

import com.google.common.eventbus.EventBus;
import com.google.inject.Provides;
import com.google.inject.matcher.Matchers;
import com.nerdery.powwow.PowWowServer;
import com.nerdery.powwow.PowWowState;
import com.nerdery.powwow.audio.AudioModule;
import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.side.PowWowSide;
import com.nerdery.powwow.side.PowWowSideOnly;
import com.nerdery.powwow.util.AtomicReferenceProvider;

public final class PowWowServerModule
extends AudioModule{
    public PowWowServerModule(){
        super(PowWowSide.REMOTE);
    }

    @Override
    protected void configure() {
        super.configure();
        this.bind(PowWowNetwork.class).to(ServerNetwork.class);
        this.bind(AudioSys.class).to(ServerAudioSys.class);
        this.bind(PowWowState.class).toProvider(new AtomicReferenceProvider<>(PowWowServer.stateRef));
        this.bindInterceptor(Matchers.any(), Matchers.annotatedWith(PowWowSideOnly.class), this.sideOnlyInterceptor);
    }

    @Provides
    public EventBus provideEventBus(){
        return PowWowServer.events;
    }
}