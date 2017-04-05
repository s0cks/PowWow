package com.nerdery.powwow;

import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nerdery.powwow.audio.AudioIO;
import com.nerdery.powwow.client.PowWowClientModule;
import com.nerdery.powwow.net.ClientNetwork;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.packet.PowWowPacketModule;
import com.nerdery.powwow.packet.packets.UserPacket;
import com.nerdery.powwow.thread.PowWowThreadsModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

public final class PowWowClient{
    public static final AtomicReference<String> userRef = new AtomicReference<>();
    public static final AtomicReference<PowWowState> stateRef = new AtomicReference<>(PowWowState.RUNNING);
    public static final EventBus events = new EventBus();
    public static final Injector injector = Guice.createInjector(
            new PowWowDefaultModule(),
            new PowWowPacketModule(),
            new PowWowThreadsModule(),
            new PowWowClientModule()
    );

    public static void main(String... args) throws IOException{
        System.out.println("Name: ");
        System.out.println("\t>> ");
        String name = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        userRef.set(name);

        ClientEventHandler handler = injector.getInstance(ClientEventHandler.class);
        events.register(handler);

        PowWowNetwork network = injector.getInstance(PowWowNetwork.class);
        network.connect();

        ClientNetwork client = ((ClientNetwork) network);
        client.send(UserPacket.userConnect(name));

        AudioIO audio = injector.getInstance(AudioIO.class);
        audio.initialize();
    }
}