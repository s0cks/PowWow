package com.nerdery.powwow;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.nerdery.powwow.audio.AudioSide;
import com.nerdery.powwow.audio.AudioSys;
import com.nerdery.powwow.client.PowWowClientModule;
import com.nerdery.powwow.event.PowWowEventHandler;
import com.nerdery.powwow.inject.PowWowDefaultModule;
import com.nerdery.powwow.packet.PowWowPacketModule;

import java.io.IOException;

public final class PowWowClient{
    public static final EventBus events = new EventBus();
    public static final Injector injector = Guice.createInjector(
            new PowWowDefaultModule(),
            new PowWowPacketModule(),
            new PowWowClientModule()
    );

    public static void main(String... args) throws IOException {
        events.register(new PowWowEventHandler());

        Thread thread = injector.getInstance(Key.get(Thread.class, AudioSide.CLIENT.only()));
        thread.start();
        injector.getInstance(AudioSys.class).input().startCapture();
    }
}