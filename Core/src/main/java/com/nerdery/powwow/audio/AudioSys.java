package com.nerdery.powwow.audio;

import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioOutput;

import javax.inject.Provider;

public interface AudioSys
extends Provider<AudioSide> {
    public AudioInput input();
    public AudioOutput output();
}