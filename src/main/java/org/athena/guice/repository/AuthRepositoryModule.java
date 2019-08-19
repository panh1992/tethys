package org.athena.guice.repository;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.athena.auth.db.ResourceRepository;
import org.athena.auth.db.RoleRepository;
import org.athena.auth.db.UserRepository;
import org.jdbi.v3.core.Jdbi;

public class AuthRepositoryModule extends AbstractModule {

    @Inject
    @Provides
    public UserRepository userRepository(Jdbi jdbi) {
        return jdbi.onDemand(UserRepository.class);
    }

    @Inject
    @Provides
    public RoleRepository roleRepository(Jdbi jdbi) {
        return jdbi.onDemand(RoleRepository.class);
    }

    @Inject
    @Provides
    public ResourceRepository resourceRepository(Jdbi jdbi) {
        return jdbi.onDemand(ResourceRepository.class);
    }

}
