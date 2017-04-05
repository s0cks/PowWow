package com.nerdery.powwow;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public final class PowWowDefaultModule
extends AbstractModule{
    @Override
    protected void configure() {
        this.bind(Integer.class)
                .annotatedWith(Names.named(PowWowNames.AUDIO_SYS_BUFFER_SIZE))
                .toInstance(1024);
    }
}