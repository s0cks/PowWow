package com.nerdery.powwow.audio;

import com.google.inject.Inject;
import com.nerdery.powwow.PowWowClient;
import com.nerdery.powwow.audio.io.AudioInput;
import com.nerdery.powwow.audio.io.AudioInputProvider;
import com.nerdery.powwow.event.AudioReadEvent;
import com.nerdery.powwow.inject.PowWowNames;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public final class ClientAudioInput implements AudioInput, AudioInputProvider{
    private final TargetDataLine targetLine;
    private final SourceDataLine sourceLine;
    private final ByteBuffer readBuffer;
    private final int readBufferSize;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final AtomicBoolean capturing = new AtomicBoolean(false);

    @Inject
    private ClientAudioInput(AudioFormat format, @Named(PowWowNames.AUDIO_SYS_BUFFER_SIZE) int bufferSize){
        try{
            this.targetLine = AudioSystem.getTargetDataLine(format);
            this.targetLine.open(format);
            this.sourceLine = AudioSystem.getSourceDataLine(format);
        } catch(Exception e){
            throw new RuntimeException(e);
        }

        this.readBufferSize = bufferSize;
        this.readBuffer = ByteBuffer.allocateDirect(bufferSize);
    }

    @Override
    public void startCapture() throws IOException {
        if(this.capturing.get()) return;
        this.capturing.set(true);
    }

    @Override
    public void stopCapture() throws IOException {
        if(!this.capturing.get()) return;
        this.capturing.set(true);
    }

    @Override
    public void close() throws IOException {
        this.sourceLine.close();
        this.targetLine.close();
        this.readBuffer.clear();
        this.running.set(false);
        this.capturing.set(false);
    }

    @Override
    public void run() {
        while(this.running.get()){
            if(this.capturing.get()){
                byte[] bytes = new byte[this.readBufferSize];
                this.targetLine.read(bytes, 0, this.readBufferSize);
                this.readBuffer.flip();
                System.out.println("[INPUT] Read " + bytes.length + "bytes");
                PowWowClient.events.post(PowWowClient.injector.getInstance(AudioReadEvent.class));
                this.readBuffer.clear();
            }
        }
    }

    @Override
    public ByteBuffer get() {
        return this.readBuffer;
    }
}