package org.athena.config.authenticate;

import io.dropwizard.auth.UnauthorizedHandler;
import org.athena.db.UserRepository;

import javax.annotation.Nullable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Optional;

/**
 * jwt 认证过滤器
 */
public class JWTAuthorizationFilter implements ContainerRequestFilter {

    private static final String PREFIX = "";

    private static final String REALM = "JWT";

    private JWTAuthenticator authenticator;

    private UserAuthorizer authorizer;

    private UnauthorizedHandler unauthorizedHandler;

    /**
     * 构造 jwt 认证过滤器
     *
     * @param userRepository 用户 Repository
     */
    public JWTAuthorizationFilter(UserRepository userRepository) {
        this.authenticator = new JWTAuthenticator(userRepository);
        this.authorizer = new UserAuthorizer();
        this.unauthorizedHandler = new UnAuthorizedResourceHandler();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        final JWTCredentials credentials =
                getCredentials(requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));

        if (credentials == null) {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(PREFIX, REALM));
        }

        final Optional<AuthUser> principal = authenticator.authenticate(credentials);
        if (!principal.isPresent()) {
            throw new WebApplicationException(unauthorizedHandler.buildResponse(PREFIX, REALM));
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
                return REALM;
            }
        });

    }

    @Nullable
    private JWTCredentials getCredentials(String header) {
        if (header == null) {
            return null;
        }
        return new JWTCredentials(header);
    }

}
