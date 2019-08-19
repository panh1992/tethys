package org.athena.guice.jersey2;

import com.google.inject.AbstractModule;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * Bindings declared in {@link JerseyModule}s are excluded from
 * being exposed to HK2's {@link ServiceLocator}.
 */
public abstract class JerseyModule extends AbstractModule {

}
