package org.athena.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.athena.dto.ErrorDTO;
import org.athena.util.Constant;
import org.athena.util.JWTUtil;
import org.athena.util.SystemContext;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * jwt 认证过滤器
 */
public class JWTAuthorizationFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("JWTAuthorizationFilter 开始");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorizationToken = request.getHeader(Constant.AUTHORIZATION_HEADER);
        JwtClaims claims;
        try {
            claims = JWTUtil.validation(authorizationToken, Constant.AUTHORIZATION_DURATION);
            String userId = claims.getSubject();
            SystemContext.setUserId(userId);
            logger.info("登录用户id: {}", userId);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidJwtException | MalformedClaimException e) {
            logger.error("认证信息解析失败, Authorization token: {}, Exception Message: {}",
                    authorizationToken, e.getMessage());
            ErrorDTO errorDTO = ErrorDTO.builder().code(Response.Status.UNAUTHORIZED.toString())
                    .message(Response.Status.UNAUTHORIZED.toString())
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
            response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(objectMapper.writeValueAsString(errorDTO));
            }
        } finally {
            SystemContext.removeUserId();
        }
    }

    @Override
    public void destroy() {
        logger.info("JWTAuthorizationFilter 销毁");
    }

}
