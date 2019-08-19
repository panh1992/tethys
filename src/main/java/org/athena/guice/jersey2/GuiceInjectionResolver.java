package org.athena.guice.jersey2;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

import com.google.inject.Guice;

/**
 * The {@link GuiceInjectionResolver} delegates all {@link Guice}'s {@link com.google.inject.Inject}
 * binding annotations to JSR-330's {@link javax.inject.Inject}.
 *
 * @see GuiceThreeThirtyResolver
 */
public class GuiceInjectionResolver extends AbstractInjectionResolver<com.google.inject.Inject> {

    /**
     * The name of the {@link InjectionResolver} for {@link Guice}'s own
     * {@link com.google.inject.Inject} binding annotation. It's just a
     * delegate to {@link InjectionResolver#SYSTEM_RESOLVER_NAME} and doesn't
     * do anything special.
     */
    public static final String GUICE_RESOLVER_NAME = "GuiceInjectionResolver";

    private final ActiveDescriptor<? extends InjectionResolver<?>> descriptor;

    public GuiceInjectionResolver(ActiveDescriptor<? extends InjectionResolver<?>> descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        InjectionResolver<?> resolver = descriptor.create(root);
        return resolver.resolve(injectee, root);
    }

}
