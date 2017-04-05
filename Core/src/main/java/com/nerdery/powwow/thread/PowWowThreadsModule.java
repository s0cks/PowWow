package com.nerdery.powwow.thread;

import com.google.inject.AbstractModule;
import com.nerdery.powwow.side.PowWowSideOnlyThreadFactory;

import java.util.concurrent.ThreadFactory;

public final class PowWowThreadsModule
extends AbstractModule{
    @Override
    protected void configure() {
        this.bind(ThreadFactory.class).to(PowWowSideOnlyThreadFactory.class);
    }
}