package org.athena.config;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.UnauthorizedHandler;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.athena.business.FileBusiness;
import org.athena.business.UserBusiness;
import org.athena.config.authenticate.AuthUser;
import org.athena.config.authenticate.JWTAuthorizationFilter;
import org.athena.config.authenticate.UnAuthorizedResourceHandler;
import org.athena.config.plugin.InstantPlugin;
import org.athena.config.redis.RedisManaged;
import org.athena.db.FileRepository;
import org.athena.db.UserRepository;
import org.athena.resource.FileResource;
import org.athena.resource.HomeResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jpa.JpaPlugin;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * 环境配置类
 */
public final class EnvConfig {

    private EnvConfig() {
    }

    /**
     * 注册 Manage
     */
    public static void registerManage(AthenaConfiguration configuration, Environment environment) {

        environment.lifecycle().manage(new RedisManaged(configuration.getRedis()));

    }

    /**
     * 注册过滤器
     */
    public static void registerFilter(Environment environment) {

        // 暂时开启 CORS 跨域、 正式环境使用 nginx 配置
        addCors(environment);

    }

    /**
     * 注册处理资源
     */
    public static void registerResource(AthenaConfiguration configuration, Environment environment) {

        JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDatabase(), "postgres");
        jdbi.installPlugin(new InstantPlugin());
        jdbi.installPlugin(new JpaPlugin());

        final UserRepository userRepository = jdbi.onDemand(UserRepository.class);
        final FileRepository fileRepository = jdbi.onDemand(FileRepository.class);

        UserBusiness userBusiness = new UserBusiness(userRepository);
        FileBusiness fileBusiness = new FileBusiness(fileRepository);

        HomeResource homeResource = new HomeResource(userBusiness);
        FileResource fileResource = new FileResource(fileBusiness);

        JerseyEnvironment jerseyEnvironment = environment.jersey();
        jerseyEnvironment.register(homeResource);
        jerseyEnvironment.register(fileResource);

        registerAuthRelated(environment, userRepository);

    }

    /**
     * 注册异常处理类
     */
    public static void registerException(Environment environment) {

        environment.jersey().register(new BusinessExceptionMapper(environment.metrics()));

    }

    /**
     * 添加 cors 跨域 过滤器
     */
    private static void addCors(Environment environment) {

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "DNT, User-Agent, X-Requested-With, "
                + "If-Modified-Since, Cache-Control, Content-Type, Range, Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS, GET, PUT, PATCH, POST, DELETE, HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

    private static void registerAuthRelated(Environment environment, UserRepository userRepository) {

        UnauthorizedHandler unauthorizedHandler = new UnAuthorizedResourceHandler();

        JWTAuthorizationFilter jwtAuthorizationFilter = new JWTAuthorizationFilter(userRepository);

        environment.jersey().register(new AuthDynamicFeature(jwtAuthorizationFilter));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder(AuthUser.class));

        environment.jersey().register(unauthorizedHandler);

    }

}
