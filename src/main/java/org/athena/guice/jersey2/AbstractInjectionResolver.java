package org.athena.guice.jersey2;

import org.glassfish.hk2.api.InjectionResolver;

/**
 * An abstract implementation of {@link InjectionResolver}.
 */
abstract class AbstractInjectionResolver<T> implements InjectionResolver<T> {

    @Override
    public boolean isConstructorParameterIndicator() {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

}
