package com.nerdery.powwow.side;

import java.lang.annotation.Annotation;

final class PowWowSideOnlyImpl
implements PowWowSideOnly{
    private final PowWowSide side;

    protected PowWowSideOnlyImpl(PowWowSide side){
        this.side = side;
    }

    @Override
    public int hashCode() {
        return (127 * "value".hashCode()) ^ this.value().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PowWowSideOnly)) return false;
        PowWowSideOnly sideOnly = ((PowWowSideOnly) obj);
        return sideOnly.value().equals(this.value());
    }

    @Override
    public PowWowSide value() {
        return this.side;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return PowWowSideOnly.class;
    }
}