package org.athena.config;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.athena.dto.ErrorDTO;
import org.athena.exceptions.BusinessException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 参数错误异常映射
 */
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private final Meter exceptions;

    BusinessExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(MetricRegistry.name(getClass(), "business exceptions"));
    }

    @Override
    public Response toResponse(BusinessException exception) {
        exceptions.mark();
        return Response.status(exception.getStatus())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(ErrorDTO.builder().code(exception.getCode())
                        .message(exception.getMessage()).build())
                .build();
    }

}
