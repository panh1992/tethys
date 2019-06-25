package org.athena.config.configuration;

import lombok.Data;

@Data
public class RabbitmqConfiguration {

    private String host;

    private String username;

    private String password;

    private String virtualHost;

}
