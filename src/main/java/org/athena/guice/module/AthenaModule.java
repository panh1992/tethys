package org.athena.guice.module;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.athena.config.Configuration;
import org.athena.jdbi.UserRepository;
import org.jdbi.v3.core.Jdbi;

public class AthenaModule implements Module {

    @Provides
    private JdbiFactory dbiFactory() {
        return new JdbiFactory();
    }

    @Inject
    @Provides
    private Jdbi jdbi(Configuration configuration, Environment environment, JdbiFactory factory) {
        return factory.build(environment, configuration.getDatabase(), "postgres");
    }

    @Inject
    @Provides
    public UserRepository userRepository(Jdbi jdbi) {
        return jdbi.onDemand(UserRepository.class);
    }

    @Override
    public void configure(Binder binder) {
    }

}
