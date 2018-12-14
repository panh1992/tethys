package org.athena.config;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import org.athena.dto.ErrorDTO;
import org.athena.exceptions.BusinessException;
import org.athena.exceptions.InternalServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 参数错误异常映射
 */
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private static Logger logger = LoggerFactory.getLogger(BusinessExceptionMapper.class);

    private final Meter exceptions;

    BusinessExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(MetricRegistry.name(getClass(), "business exceptions"));
    }

    @Override
    public Response toResponse(BusinessException exception) {
        exceptions.mark();
        logger.info("BusinessException type:{} status {} code:{} message:{}", exception.getClass().getName(),
                exception.getStatus(), exception.getCode(), exception.getMessage());
        ErrorDTO errorDTO = ErrorDTO.builder().code(exception.getCode())
                .message(exception instanceof InternalServerError
                        ? Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase() : exception.getMessage())
                .build();
        return Response.status(exception.getStatus())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorDTO)
                .build();
    }

}
