package com.nerdery.powwow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.nerdery.powwow.event.PowWowAudioEvent;
import com.nerdery.powwow.net.ClientNetwork;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.packet.packets.AudioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
final class ClientEventHandler {
    private final Logger logger = LoggerFactory.getLogger(ClientEventHandler.class);
    private final EventBus bus;
    private final ClientNetwork client;
    private final Provider<String> userProvider;

    @Inject
    private ClientEventHandler(EventBus bus, PowWowNetwork network, @Named(PowWowNames.CLIENT_USER_NAME) Provider<String> userProvider){
        this.bus = bus;
        this.client = ((ClientNetwork) network);
        this.userProvider = userProvider;
    }

    @Subscribe
    public void onAudioPacket(AudioPacket packet){
        this.logger.info(packet.from + ">> " + packet.audio.length + " bytes");
    }

    @Subscribe
    public void onAudioCapture(PowWowAudioEvent.PowWowAudioCaptureEvent e){
        this.client.send(new AudioPacket(this.userProvider.get(), e.bytes));
    }
}