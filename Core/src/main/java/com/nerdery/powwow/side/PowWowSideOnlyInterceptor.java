package com.nerdery.powwow.side;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

public final class PowWowSideOnlyInterceptor
        implements MethodInterceptor {
    private final PowWowSide side;

    public PowWowSideOnlyInterceptor(PowWowSide side) {
        this.side = side;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method m = invocation.getMethod();
        PowWowSideOnly sideOnly = m.getDeclaredAnnotation(PowWowSideOnly.class);
        if (!sideOnly.value().equals(this.side)) {
            return null;
        }
        return invocation.proceed();
    }
}