package org.athena.guice.jersey2;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.annotations.Service;

import java.lang.annotation.Annotation;

@Service
public class GuiceScopeContext implements Context<GuiceScope> {

    @Override
    public Class<? extends Annotation> getScope() {
        return GuiceScope.class;
    }

    @Override
    public <U> U findOrCreate(ActiveDescriptor<U> descriptor, ServiceHandle<?> root) {
        return descriptor.create(root);
    }

    @Override
    public boolean containsKey(ActiveDescriptor<?> descriptor) {
        return false;
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> descriptor) {
    }

    @Override
    public boolean supportsNullCreation() {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void shutdown() {
    }

}
