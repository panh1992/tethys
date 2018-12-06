package org.athena.exceptions;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 参数错误异常
 */
@Getter
public class InvalidParameter extends BusinessException {

    private InvalidParameter(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static InvalidParameter build(String message) {
        return new InvalidParameter(Response.Status.BAD_REQUEST, "InvalidParameter", message);
    }

    public static InvalidParameter build(String code, String message) {
        return new InvalidParameter(Response.Status.BAD_REQUEST, code, message);
    }

    public static InvalidParameter build(Response.Status status, String code, String message) {
        return new InvalidParameter(status, code, message);
    }

}
