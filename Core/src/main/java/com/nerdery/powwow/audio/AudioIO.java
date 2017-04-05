package com.nerdery.powwow.audio;

import com.google.common.eventbus.EventBus;
import com.nerdery.powwow.PowWowState;
import com.nerdery.powwow.event.PowWowAudioEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.ThreadFactory;

@Singleton
public final class AudioIO{
    private final AudioSys sys;
    private final EventBus events;
    private final Provider<PowWowState> stateProvider;
    private final Thread readThread;
    private final Thread writeThread;

    @Inject
    private AudioIO(AudioSys sys, ThreadFactory threads, EventBus bus, Provider<PowWowState> stateProvider){
        this.sys = sys;
        this.events = bus;
        this.stateProvider = stateProvider;
        this.readThread = threads.newThread(new AudioInputThread(this));
        this.writeThread = threads.newThread(new AudioOutputThread(this));
    }

    public void initialize(){
        this.readThread.start();
    }

    protected static abstract class AudioThread
    implements Runnable{
        protected final AudioIO audio;

        protected AudioThread(AudioIO io){
            this.audio = io;
        }
    }

    private static final class AudioOutputThread
    extends AudioThread{
        private final Logger logger = LoggerFactory.getLogger(AudioOutputThread.class);

        private AudioOutputThread(AudioIO io){
            super(io);
        }

        @Override
        public void run() {
            try{
                this.audio.events.post(PowWowAudioEvent.transition(PowWowAudioEvent.Type.NOT_CAPTURING, PowWowAudioEvent.Type.CAPTURING));
            } catch(Exception e){
                this.logger.error("Unable to provide sound output", e);
                //TODO: Handle exception better
            } finally{
                this.audio.events.post(PowWowAudioEvent.transition(PowWowAudioEvent.Type.CAPTURING, PowWowAudioEvent.Type.NOT_CAPTURING));
            }
        }
    }

    private static final class AudioInputThread
    extends AudioThread{
        private final Logger logger = LoggerFactory.getLogger(AudioInputThread.class);

        private AudioInputThread(AudioIO io){
            super(io);
        }

        @Override
        public void run() {
            try{
                this.audio.events.post(PowWowAudioEvent.transition(PowWowAudioEvent.Type.NOT_CAPTURING, PowWowAudioEvent.Type.CAPTURING));
                while(this.audio.stateProvider.get() != PowWowState.STOPPED){
                    byte[] buffer = this.audio.sys.buffer();
                    this.audio.sys.input().read(buffer, 0, buffer.length);
                    this.audio.events.post(PowWowAudioEvent.capture(buffer));
                }
            } catch(Exception e){
                this.logger.error("Unable to capture sound", e);
                //TODO: Handle exception better
            } finally{
                this.audio.events.post(PowWowAudioEvent.transition(PowWowAudioEvent.Type.CAPTURING, PowWowAudioEvent.Type.NOT_CAPTURING));
            }
        }
    }
}