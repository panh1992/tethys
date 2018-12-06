package org.athena;

import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.business.UserBusiness;
import org.athena.config.AthenaConfiguration;
import org.athena.config.EnvConfig;
import org.athena.config.plugin.InstantPlugin;
import org.athena.db.UserRepository;
import org.athena.resources.HomeResource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jpa.JpaPlugin;

public class AthenaApplication extends Application<AthenaConfiguration> {

    public static void main(String[] args) throws Exception {

        new AthenaApplication().run(args);

    }

    @Override
    public void initialize(Bootstrap<AthenaConfiguration> bootstrap) {

        bootstrap.addBundle(new MultiPartBundle());
        bootstrap.addBundle(new JdbiExceptionsBundle());

    }

    @Override
    public void run(AthenaConfiguration athenaConfiguration, Environment environment) {

        EnvConfig.configuration(environment);

        JdbiFactory jdbiFactory = new JdbiFactory();

        Jdbi jdbi = jdbiFactory.build(environment, athenaConfiguration.getDatabase(), "postgres");

        jdbi.installPlugin(new InstantPlugin());
        jdbi.installPlugin(new JpaPlugin());

        HomeResource homeResource = new HomeResource(new UserBusiness(jdbi.onDemand(UserRepository.class)));

        environment.jersey().register(homeResource);

    }

}
