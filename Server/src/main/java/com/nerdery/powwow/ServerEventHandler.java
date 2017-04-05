package com.nerdery.powwow;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.nerdery.powwow.data.User;
import com.nerdery.powwow.net.PowWowNetwork;
import com.nerdery.powwow.net.ServerNetwork;
import com.nerdery.powwow.packet.PowWowPacket;
import com.nerdery.powwow.packet.packets.AudioPacket;
import com.nerdery.powwow.packet.packets.UserPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
final class ServerEventHandler{
    private final Logger logger = LoggerFactory.getLogger(ServerEventHandler.class);
    private final EventBus events;
    private final ServerNetwork server;
    private final Map<String, User> userMap = new HashMap<>();

    @Inject
    private ServerEventHandler(EventBus events, PowWowNetwork network){
        this.events = events;
        this.server = ((ServerNetwork) network);
    }

    @Subscribe
    public void onChannelSpecific(PowWowPacket.ChannelSpecificPacket pck){
        if(pck.owner instanceof UserPacket){
            UserPacket usr = ((UserPacket) pck.owner);
            this.logger.info(usr.user + " Joined!");
            this.userMap.put(usr.user, new User(pck.channel));
        }
    }

    @Subscribe
    public void onAudioPacket(AudioPacket pck){
        this.userMap.entrySet().forEach((e)->{
            if(e.getKey().equalsIgnoreCase(pck.from)) return;
            e.getValue().send(pck);
        });
    }
}