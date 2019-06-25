package org.athena.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 资源过滤器，用于资源、权限校验
 */
public class ResourceFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ResourceFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("ResourceFilter 开始");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        logger.info("ResourceFilter 销毁");
    }
}
