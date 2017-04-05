package com.nerdery.powwow.event;

import com.nerdery.powwow.net.PowWowNetwork;

public final class PowWowNetworkEvent{
    public final PowWowNetwork.State from;
    public final PowWowNetwork.State to;

    public PowWowNetworkEvent(PowWowNetwork.State from, PowWowNetwork.State to){
        this.from = from;
        this.to = to;
    }
}