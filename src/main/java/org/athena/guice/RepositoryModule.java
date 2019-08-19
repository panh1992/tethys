package org.athena.guice;

import com.google.inject.AbstractModule;
import org.athena.guice.repository.AuthRepositoryModule;
import org.athena.guice.repository.StorageRepositoryModule;

public class RepositoryModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new AuthRepositoryModule());
        install(new StorageRepositoryModule());
    }

}
