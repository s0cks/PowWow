package com.nerdery.powwow.net;

import com.google.common.eventbus.EventBus;
import com.nerdery.powwow.event.PowWowNetworkEvent;

import java.util.concurrent.atomic.AtomicReference;

public abstract class PowWowNetwork{
    private final AtomicReference<State> stateRef = new AtomicReference<>(State.DISCONNECTED);
    private final EventBus events;

    protected PowWowNetwork(EventBus events){
        this.events = events;
    }

    public abstract void connect();
    public abstract void disconnect();

    protected final void transition(State to){
        State prev = this.stateRef.get();
        this.events.post(new PowWowNetworkEvent(prev, to));
        this.stateRef.set(to);
    }

    public final State getState(){
        return this.stateRef.get();
    }

    public enum State{
        STARTING,
        CONNECTED,
        STOPPING,
        DISCONNECTED;
    }
}