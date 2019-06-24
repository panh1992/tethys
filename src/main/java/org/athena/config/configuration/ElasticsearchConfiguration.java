package org.athena.config.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ElasticsearchConfiguration {

    @NotEmpty
    private String hostname;

    @NotEmpty
    private int port;

    @NotEmpty
    private String scheme;

}
