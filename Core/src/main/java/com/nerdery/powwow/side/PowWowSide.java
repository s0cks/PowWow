package com.nerdery.powwow.side;

public enum PowWowSide{
    LOCAL,
    REMOTE,
    BOTH;

    public final PowWowSideOnly only(){
        return new PowWowSideOnlyImpl(this);
    }
}