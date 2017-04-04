package com.nerdery.powwow.audio.io;

import java.io.Closeable;
import java.io.IOException;

public interface AudioInput extends Closeable, Runnable{
    public void startCapture() throws IOException;
    public void stopCapture() throws IOException;
}