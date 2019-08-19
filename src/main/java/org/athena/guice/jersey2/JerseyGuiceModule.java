package org.athena.guice.jersey2;

import com.google.inject.Injector;
import com.google.inject.servlet.RequestScoped;
import org.glassfish.hk2.api.ServiceLocator;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Providers;

public class JerseyGuiceModule extends JerseyModule {

    private final ServiceLocator locator;

    public JerseyGuiceModule(String name) {
        this(JerseyGuiceUtils.newServiceLocator(name));
    }

    public JerseyGuiceModule(ServiceLocator locator) {
        this.locator = locator;
    }

    @Override
    protected void configure() {

        Provider<Injector> injector = getProvider(Injector.class);
        bind(ServiceLocator.class).toProvider(new ServiceLocatorProvider(injector, locator)).in(Singleton.class);

        Provider<ServiceLocator> provider = getProvider(ServiceLocator.class);

        bind(Application.class).toProvider(new JerseyProvider<>(provider, Application.class));

        bind(Providers.class).toProvider(new JerseyProvider<>(provider, Providers.class));

        bind(UriInfo.class).toProvider(new JerseyProvider<>(provider, UriInfo.class)).in(RequestScoped.class);

        bind(HttpHeaders.class).toProvider(new JerseyProvider<>(provider, HttpHeaders.class)).in(RequestScoped.class);

        bind(SecurityContext.class).toProvider(new JerseyProvider<>(provider, SecurityContext.class))
                .in(RequestScoped.class);

        bind(Request.class).toProvider(new JerseyProvider<>(provider, Request.class)).in(RequestScoped.class);

    }

    private static class JerseyProvider<T> implements Provider<T> {

        private Provider<ServiceLocator> provider;

        private final Class<T> type;

        public JerseyProvider(Provider<ServiceLocator> provider, Class<T> type) {
            this.provider = provider;
            this.type = type;
        }

        @Override
        public T get() {
            ServiceLocator locator = provider.get();
            return locator.getService(type);
        }
    }

    private static class ServiceLocatorProvider implements Provider<ServiceLocator> {

        private final Provider<Injector> provider;

        private final ServiceLocator locator;

        @Inject
        public ServiceLocatorProvider(Provider<Injector> provider, ServiceLocator locator) {
            this.provider = provider;
            this.locator = locator;
        }

        @Override
        public ServiceLocator get() {
            Injector injector = provider.get();
            JerseyGuiceUtils.link(locator, injector);

            return locator;
        }
    }
}
