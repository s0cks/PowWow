package com.nerdery.powwow.client;

import com.nerdery.powwow.audio.io.AudioInput;

import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.IOException;

final class AudioInputJavaSound
implements AudioInput {
    private final AudioFormat format;
    private final TargetDataLine targetLine;

    @Inject
    private AudioInputJavaSound(AudioFormat format) {
        try {
            this.targetLine = AudioSystem.getTargetDataLine(format);
            this.format = format;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.targetLine.close();
    }

    @Override
    public void open() throws IOException {
        try {
            this.targetLine.open(this.format);
        } catch (LineUnavailableException e) {
            throw new IOException("Cannot open target line", e);
        }
    }

    @Override
    public void read(byte[] bytes, int off, int len) throws IOException {
        this.targetLine.read(bytes, off, len);
    }
}