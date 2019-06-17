package org.athena.config;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CorsConfig {

    @NotNull
    private String allowedOrigins;

    @NotNull
    private String allowedHeaders;

    @NotNull
    private String allowedMethods;

    @NotNull
    private String[] urlPatterns;

}
