package org.athena.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class LoadBalancerPing extends HealthCheck {

    @Override
    protected Result check() throws Exception {

        return Result.healthy();

    }

}
