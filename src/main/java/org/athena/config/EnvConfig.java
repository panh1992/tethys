package org.athena.config;

import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import org.athena.auth.business.UserBusiness;
import org.athena.auth.db.UserRepository;
import org.athena.auth.resource.HomeResource;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.config.configuration.AthenaConfiguration;
import org.athena.config.configuration.CorsConfiguration;
import org.athena.config.exception.BusinessExceptionMapper;
import org.athena.config.exception.ValidationExceptionMapper;
import org.athena.config.managed.NettyManaged;
import org.athena.config.managed.RabbitmqManaged;
import org.athena.config.managed.RedisManaged;
import org.athena.config.managed.SchedulerManaged;
import org.athena.config.plugin.InstantPlugin;
import org.athena.filter.JWTAuthorizationFilter;
import org.athena.filter.ResourceFilter;
import org.athena.storage.business.FileBusiness;
import org.athena.storage.db.FileRepository;
import org.athena.storage.resource.FileResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jdbi.v3.core.Jdbi;
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

        environment.lifecycle().manage(new RabbitmqManaged(configuration.getRabbitmq()));

        environment.lifecycle().manage(new SchedulerManaged());

        environment.lifecycle().manage(new NettyManaged());

    }

    /**
     * 注册过滤器
     */
    public static void registerFilter(Environment environment) {

        environment.servlets().addFilter("Authorization", JWTAuthorizationFilter.class)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/list");

        environment.servlets().addFilter("Resource", ResourceFilter.class)
                .addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/list");

    }

    /**
     * 开启 CORS 跨域
     */
    public static void registerCors(Environment environment, CorsConfiguration cors) {

        final FilterRegistration.Dynamic dynamic =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        dynamic.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, cors.getAllowedOrigins());
        dynamic.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, cors.getAllowedHeaders());
        dynamic.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, cors.getAllowedMethods());

        dynamic.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, cors.getUrlPatterns());

    }

    /**
     * 注册处理资源
     */
    public static void registerResource(AthenaConfiguration configuration, Environment environment) {

        JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDatabase(), "postgres");
        jdbi.installPlugin(new InstantPlugin());

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

}
