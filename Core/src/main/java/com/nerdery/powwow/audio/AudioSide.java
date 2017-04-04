package com.nerdery.powwow.audio;

public enum AudioSide{
    CLIENT,
    SERVER,
    UNKNOWN;

    public AudioSideOnly only(){
        return new AudioSideOnlyImpl(this);
    }
}