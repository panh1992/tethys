package org.athena.exceptions;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 系统错误
 */
@Getter
public class InternalError extends BusinessException {

    private InternalError(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static InternalError build(String code, String message) {
        return new InternalError(Response.Status.INTERNAL_SERVER_ERROR, code, message);
    }

    public static InternalError build(String message) {
        return new InternalError(Response.Status.BAD_REQUEST, Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), message);
    }

}
