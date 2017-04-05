package com.nerdery.powwow.util;

import javax.inject.Provider;
import java.util.concurrent.atomic.AtomicReference;

public final class AtomicReferenceProvider<T>
implements Provider<T>{
    private final AtomicReference<T> tRef;

    public AtomicReferenceProvider(AtomicReference<T> tRef){
        this.tRef = tRef;
    }

    @Override
    public T get() {
        return this.tRef.get();
    }
}