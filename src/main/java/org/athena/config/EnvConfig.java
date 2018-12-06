package org.athena.config;

import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * 环境配置类
 */
public final class EnvConfig {

    private EnvConfig() {
    }

    public static void configuration(Environment environment) {

        // 暂时开启 CORS 跨域、 正式环境使用 nginx 配置
        addCors(environment);

        registerException(environment);

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

    /**
     * 注册异常处理类
     */
    private static void registerException(Environment environment) {

        environment.jersey().register(new BusinessExceptionMapper(environment.metrics()));

    }

}
