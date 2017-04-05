package com.nerdery.powwow;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.packet.PowWowPacketModule;
import com.nerdery.powwow.server.PowWowServerModule;
import com.nerdery.powwow.thread.PowWowThreadsModule;

import java.util.concurrent.atomic.AtomicReference;

public final class PowWowServer{
    public static final AtomicReference<PowWowState> stateRef = new AtomicReference<>(PowWowState.RUNNING);
    public static final EventBus events = new EventBus();
    public static final Injector injector = Guice.createInjector(
            new PowWowDefaultModule(),
            new PowWowPacketModule(),
            new PowWowThreadsModule(),
            new PowWowServerModule()
    );

    public static void main(String... args) throws Exception{
        ServerEventHandler handler = injector.getInstance(ServerEventHandler.class);
        events.register(handler);

        PowWowNetwork network = injector.getInstance(PowWowNetwork.class);
        network.connect();
    }
}