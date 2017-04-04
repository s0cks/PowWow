package com.nerdery.powwow.event;

import com.google.common.eventbus.Subscribe;

public final class PowWowEventHandler{
    @Subscribe
    public void onAudioRead(AudioReadEvent e){
        System.out.println("Read " + e.buffer.position());
    }

    @Subscribe
    public void onPacket(PacketEvent e){
    }
}