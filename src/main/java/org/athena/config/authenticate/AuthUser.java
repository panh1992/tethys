package org.athena.config.authenticate;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class AuthUser implements Principal {

    private String userId;

    private String userName;

    @Override
    public String getName() {
        return this.userName;
    }

    public String getUserId() {
        return userId;
    }

}
