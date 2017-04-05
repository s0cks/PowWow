package com.nerdery.powwow.audio;

import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.nerdery.powwow.side.PowWowSide;
import com.nerdery.powwow.side.PowWowSideOnlyInterceptor;

import javax.sound.sampled.AudioFormat;

public abstract class AudioModule
extends AbstractModule{
    protected final PowWowSide side;
    protected final AudioFormat format = new AudioFormat(8000, 16, 1, true, true);
    protected final PowWowSideOnlyInterceptor sideOnlyInterceptor;

    protected AudioModule(PowWowSide side){
        this.side = side;
        this.sideOnlyInterceptor = new PowWowSideOnlyInterceptor(this.side);
    }

    @Override
    protected void configure() {
        this.bind(PowWowSide.class).toInstance(this.side);
        this.bind(AudioFormat.class).toInstance(this.format);
    }

    protected final <T> LinkedBindingBuilder<T> bindSideOnly(Class<T> tClass){
        return this.bind(tClass)
                .annotatedWith(this.side.only());
    }
}