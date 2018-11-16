package org.athena;

import com.hubspot.dropwizard.guice.GuiceBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.athena.config.Configuration;
import org.athena.guice.module.AthenaModule;

public class Application extends io.dropwizard.Application<Configuration> {

    public static void main(String[] args) throws Exception {

        new Application().run(args);

    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

        bootstrap.addBundle(GuiceBundle.<Configuration>newBuilder()
                .addModule(new AthenaModule())
                .enableAutoConfig(getClass().getPackage().getName())
                .setConfigClass(Configuration.class)
                .build());

    }

    @Override
    public void run(Configuration configuration, Environment environment) {

    }

}
