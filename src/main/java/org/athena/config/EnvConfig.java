package org.athena.config;

import io.dropwizard.setup.Environment;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.athena.config.configuration.CorsConfiguration;
import org.athena.config.exception.BusinessExceptionMapper;
import org.athena.config.exception.ValidationExceptionMapper;
import org.athena.filter.JWTAuthorizationFilter;
import org.athena.filter.ResourceFilter;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.Map;

/**
 * 环境配置类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EnvConfig {

    private static Logger logger = LoggerFactory.getLogger(EnvConfig.class);

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
     * 注册过滤器
     */
    public static void registerFilter(Environment environment, Jdbi jdbi) {

        Map<String, Object> map = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM store_space")
                .mapToMap().findOnly());

        // TODO: 进行权限控制
        logger.info("权限信息：{}", map);

        FilterRegistration.Dynamic authorizationFilter = environment.servlets()
                .addFilter("Authorization", JWTAuthorizationFilter.class);
        authorizationFilter.setInitParameter("", "");
        authorizationFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true,
                "/spaces", "/spaces/*", "/files", "/files/*");

        FilterRegistration.Dynamic resourceFilter = environment.servlets()
                .addFilter("Resource", ResourceFilter.class);
        resourceFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true,
                "/*");

    }

    /**
     * 注册异常处理类
     */
    public static void registerException(Environment environment) {

        environment.jersey().register(new BusinessExceptionMapper(environment.metrics()));

        environment.jersey().register(new ValidationExceptionMapper());

    }

}
