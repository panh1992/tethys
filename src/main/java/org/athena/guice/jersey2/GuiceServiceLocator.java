package org.athena.guice.jersey2;

import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

/**
 * An extension of {@link ServiceLocatorImpl} that exist primarily for type
 * information and completeness in respect to the other classes.
 */
class GuiceServiceLocator extends ServiceLocatorImpl {

    public GuiceServiceLocator(String name, ServiceLocator parent) {
        super(name, (ServiceLocatorImpl) parent);
    }

}
