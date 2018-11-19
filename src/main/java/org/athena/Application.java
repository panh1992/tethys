package org.athena;

import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.resources.HomeResource;
import org.athena.config.AthenaCors;
import org.athena.config.Configuration;
import org.athena.health.LoadBalancerPing;
import org.athena.db.UserRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jodatime2.JodaTimePlugin;
import org.jdbi.v3.jpa.JpaPlugin;

public class Application extends io.dropwizard.Application<Configuration> {

    public static void main(String[] args) throws Exception {

        new Application().run(args);

    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

        bootstrap.addBundle(new MultiPartBundle());

    }

    @Override
    public void run(Configuration configuration, Environment environment) {

        new AthenaCors().allowRequestsFromAnywhere(environment.servlets());

        environment.healthChecks().register("ping", new LoadBalancerPing());

        JdbiFactory jdbiFactory = new JdbiFactory();

        Jdbi jdbi = jdbiFactory.build(environment, configuration.getDatabase(), "postgres");

        jdbi.installPlugin(new JodaTimePlugin());
        jdbi.installPlugin(new JpaPlugin());

        environment.jersey().register(new HomeResource(jdbi.onDemand(UserRepository.class)));

    }

}
