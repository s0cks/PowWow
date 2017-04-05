package com.nerdery.powwow.audio.io;

import java.io.Closeable;
import java.io.IOException;

public interface AudioInput
extends Closeable{
    public void open() throws IOException;
    public void read(byte[] bytes, int off, int len) throws IOException;
}