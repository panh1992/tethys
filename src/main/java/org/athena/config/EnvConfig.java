package org.athena.config;

import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.athena.business.UserBusiness;
import org.athena.config.plugin.InstantPlugin;
import org.athena.db.UserRepository;
import org.athena.resources.HomeResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;
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
        Jdbi jdbi = jdbiFactory.build(environment, configuration.getDatabase(), "postgres");
        jdbi.installPlugin(new InstantPlugin());
        jdbi.installPlugin(new JpaPlugin());

        HomeResource homeResource = new HomeResource(new UserBusiness(jdbi.onDemand(UserRepository.class)));

        environment.jersey().register(homeResource);

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

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,PATCH,POST,DELETE,HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
