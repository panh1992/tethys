package org.athena.guice.jersey2;

import com.google.inject.Guice;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.UnsatisfiedDependencyException;
import org.jvnet.hk2.internal.ThreeThirtyResolver;

import javax.annotation.Nullable;

/**
 * This is a replacement for HK2's {@link ThreeThirtyResolver}. It adds  support for JSR-305's
 * {@link Nullable} and {@link Guice}'s own {@link com.google.inject.Inject#optional()}.
 *
 * @see ThreeThirtyResolver
 * @see BindingUtils#isNullable(Injectee)
 * @see Nullable
 * @see com.google.inject.Inject#optional()
 */
public class GuiceThreeThirtyResolver extends AbstractInjectionResolver<javax.inject.Inject> {

    private final ServiceLocator locator;

    public GuiceThreeThirtyResolver(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        ActiveDescriptor<?> descriptor = locator.getInjecteeDescriptor(injectee);

        if (descriptor == null) {

            // Is it OK to return null?
            if (BindingUtils.isNullable(injectee)) {
                return null;
            }

            throw new MultiException(new UnsatisfiedDependencyException(injectee));
        }

        return locator.getService(descriptor, root, injectee);
    }

}
