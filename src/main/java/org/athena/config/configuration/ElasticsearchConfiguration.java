package org.athena.config.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ElasticsearchConfiguration {

    @NotEmpty
    private String hostname;

    @Min(0)
    @Max(65535)
    private int port = 9200;

    @NotEmpty
    private String scheme;

}
