package com.nerdery.powwow.event;

public abstract class PowWowAudioEvent {
    public enum Type {
        CAPTURING,
        NOT_CAPTURING
    }

    public static PowWowAudioEvent capture(byte[] buffer){
        return new PowWowAudioCaptureEvent(buffer);
    }

    public static PowWowAudioEvent transition(Type from, Type to){
        return new PowWowAudioCaptureTransitionEvent(from, to);
    }

    public static final class PowWowAudioCaptureTransitionEvent
    extends PowWowAudioEvent{
        public final Type from;
        public final Type to;

        private PowWowAudioCaptureTransitionEvent(Type from, Type to){
            this.from = from;
            this.to = to;
        }
    }

    public static final class PowWowAudioCaptureEvent
    extends PowWowAudioEvent{
        public final byte[] bytes;

        private PowWowAudioCaptureEvent(byte[] bytes){
            this.bytes = bytes;
        }
    }
}