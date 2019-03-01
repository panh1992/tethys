package org.athena.config.authenticate;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;

import javax.annotation.Nullable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Optional;

/**
 * jwt 认证过滤器
 */
public class JWTAuthorizationFilter<P extends Principal> extends AuthFilter<JWTCredentials, P> {

    private JWTAuthorizationFilter() {

    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final JWTCredentials credentials =
                getCredentials(requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        try {
            if (credentials == null) {
                throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
            }

            final Optional<P> principal = authenticator.authenticate(credentials);
            if (!principal.isPresent()) {
                throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
            }

            final SecurityContext securityContext = requestContext.getSecurityContext();
            final boolean secure = securityContext != null && securityContext.isSecure();

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return principal.orElse(null);
                }

                @Override
                public boolean isUserInRole(String role) {
                    return authorizer.authorize(principal.orElse(null), role);
                }

                @Override
                public boolean isSecure() {
                    return secure;
                }

                @Override
                public String getAuthenticationScheme() {
                    return realm;
                }
            });
        } catch (AuthenticationException e) {
            logger.warn("Error authenticating credentials", e);
            throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, realm));
        }
    }

    @Nullable
    private JWTCredentials getCredentials(String header) {
        if (header == null) {
            return null;
        }
        return new JWTCredentials(header);
    }

    public static class Builder<P extends Principal> extends
            AuthFilterBuilder<JWTCredentials, P, JWTAuthorizationFilter<P>> {

        @Override
        protected JWTAuthorizationFilter<P> newInstance() {
            return new JWTAuthorizationFilter<>();
        }
    }

}
