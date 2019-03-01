package org.athena.config.authenticate;

import io.dropwizard.auth.Authenticator;
import org.athena.api.User;
import org.athena.db.UserRepository;
import org.athena.util.Constant;
import org.athena.util.JWTUtil;
import org.athena.util.SystemContext;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JWTAuthenticator implements Authenticator<JWTCredentials, AuthUser> {

    private static Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private UserRepository userRepository;

    public JWTAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AuthUser> authenticate(JWTCredentials credentials) {

        String userId;
        try {
            JwtClaims claims = JWTUtil.validation(credentials.getJwtToken(), Constant.AUTHORIZATION_DURATION);
            userId = claims.getSubject();
            logger.info("登录用户id: {}", userId);
            SystemContext.setUserId(userId);
        } catch (InvalidJwtException | MalformedClaimException e) {
            logger.info("登录失败 Authorization Token: {}", credentials.getJwtToken());
            return Optional.empty();
        }

        Optional<User> optional = this.userRepository.findById(userId);
        if (optional.isPresent()) {
            User user = optional.get();
            return Optional.of(new AuthUser(user.getId(), user.getUserName()));
        }
        return Optional.empty();
    }

}