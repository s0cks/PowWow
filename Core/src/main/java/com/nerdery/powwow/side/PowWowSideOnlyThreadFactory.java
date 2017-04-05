package com.nerdery.powwow.side;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public final class PowWowSideOnlyThreadFactory
implements ThreadFactory{
    private final ThreadGroup threadGroup;
    private final AtomicInteger threadCount = new AtomicInteger(0);

    @Inject
    private PowWowSideOnlyThreadFactory(PowWowSide side){
        this.threadGroup = new ThreadGroup("PowWow-" + side + "-Threads");
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(this.threadGroup, r);
        thread.setName("Worker-" + this.threadCount.getAndIncrement());
        return thread;
    }
}