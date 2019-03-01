package org.athena.config.authenticate;

import io.dropwizard.auth.Authorizer;

public class UserAuthorizer implements Authorizer<AuthUser> {

    @Override
    public boolean authorize(AuthUser principal, String role) {
        return true;
    }

}
