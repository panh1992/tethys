package org.athena.config.authenticate;

import java.util.Objects;

public class JWTCredentials {

    private final String jwtToken;

    public JWTCredentials(String jwtToken) {
        this.jwtToken = Objects.requireNonNull(jwtToken);
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

}
