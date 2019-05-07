package org.athena.config;

import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.athena.api.business.FileBusiness;
import org.athena.api.business.UserBusiness;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.config.exception.BusinessExceptionMapper;
import org.athena.config.exception.ValidationExceptionMapper;
import org.athena.config.netty.NettyManaged;
import org.athena.config.plugin.InstantPlugin;
import org.athena.config.quartz.SchedulerManaged;
import org.athena.config.redis.RedisManaged;
import org.athena.api.db.FileRepository;
import org.athena.api.db.UserRepository;
import org.athena.filter.JWTAuthorizationFilter;
import org.athena.api.resource.FileResource;
import org.athena.api.resource.HomeResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jpa.JpaPlugin;
import org.quartz.SchedulerException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
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
    public static void registerManage(AthenaConfiguration configuration, Environment environment)
            throws SchedulerException, IOException {

        environment.lifecycle().manage(new RedisManaged(configuration.getRedis()));

        environment.lifecycle().manage(new SchedulerManaged());

        environment.lifecycle().manage(new NettyManaged());

    }

    /**
     * 注册过滤器
     */
    public static void registerFilter(Environment environment) {

        // 暂时开启 CORS 跨域、 正式环境使用 nginx 配置
        addCors(environment);

        addAuthorization(environment);

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

        final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(configuration.getSnowflake().getWorkerId(),
                configuration.getSnowflake().getDataCenterId());

        final UserBusiness userBusiness = new UserBusiness(idWorker, userRepository);
        final FileBusiness fileBusiness = new FileBusiness(fileRepository);

        final HomeResource homeResource = new HomeResource(userBusiness);
        final FileResource fileResource = new FileResource(fileBusiness);

        JerseyEnvironment jerseyEnvironment = environment.jersey();
        jerseyEnvironment.register(homeResource);
        jerseyEnvironment.register(fileResource);

    }

    /**
     * 注册异常处理类
     */
    public static void registerException(Environment environment) {

        environment.jersey().register(new BusinessExceptionMapper(environment.metrics()));

        environment.jersey().register(new ValidationExceptionMapper());

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

    /**
     * 添加 授权认证 过滤器
     */
    private static void addAuthorization(Environment environment) {
        final FilterRegistration.Dynamic authorization =
                environment.servlets().addFilter("Authorization", JWTAuthorizationFilter.class);

        authorization.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/test");
    }

}
