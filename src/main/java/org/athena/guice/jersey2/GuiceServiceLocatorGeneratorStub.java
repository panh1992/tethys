package org.athena.guice.jersey2;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extension.ServiceLocatorGenerator;

import java.util.concurrent.atomic.AtomicReference;

public class GuiceServiceLocatorGeneratorStub implements ServiceLocatorGenerator {

    private static final AtomicReference<ServiceLocatorGenerator> GENERATOR_REF = new AtomicReference<>();

    static ServiceLocatorGenerator install(ServiceLocatorGenerator generator) {
        if (generator instanceof GuiceServiceLocatorGeneratorStub) {
            throw new IllegalArgumentException();
        }

        return GENERATOR_REF.getAndSet(generator);
    }

    static ServiceLocatorGenerator get() {
        return GENERATOR_REF.get();
    }

    @Override
    public ServiceLocator create(String name, ServiceLocator parent) {
        ServiceLocatorGenerator generator = GENERATOR_REF.get();
        if (generator == null) {
            throw new IllegalStateException("It appears there is no ServiceLocatorGenerator installed.");
        }
        return generator.create(name, parent);
    }

}