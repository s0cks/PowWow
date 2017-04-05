package com.nerdery.powwow.audio;

import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioOutput;

public interface AudioSys{
    public AudioInput input();
    public AudioOutput output();
    public byte[] buffer();
}