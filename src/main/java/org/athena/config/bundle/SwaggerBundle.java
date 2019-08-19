package org.athena.config.bundle;

import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.athena.config.configuration.AthenaConfiguration;

public class SwaggerBundle extends io.federecio.dropwizard.swagger.SwaggerBundle<AthenaConfiguration> {

    @Override
    protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AthenaConfiguration configuration) {
        return configuration.getSwagger();
    }

}
