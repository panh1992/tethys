package org.athena;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.config.AthenaCors;
import org.athena.config.Configuration;
import org.athena.guice.module.AthenaModule;
import org.athena.healthcheck.LoadBalancerPing;

public class Application extends io.dropwizard.Application<Configuration> {

    public static void main(String[] args) throws Exception {

        new Application().run(args);

    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

        GuiceBundle<Configuration> guiceBundle = GuiceBundle.<Configuration>newBuilder()
                .addModule(new AthenaModule())
                .setConfigClass(Configuration.class)
                .enableAutoConfig(getClass().getPackage().getName())
                .build();

        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(new MultiPartBundle());

    }

    @Override
    public void run(Configuration configuration, Environment environment) {

        // CORS: allow requests from anywhere
        new AthenaCors().allowRequestsFromAnywhere(environment.servlets());

        // Health checks
        environment.healthChecks().register("ping", new LoadBalancerPing());

    }

}
