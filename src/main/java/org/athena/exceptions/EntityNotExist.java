package org.athena.exceptions;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 实体未找到异常
 */
@Getter
public class EntityNotExist extends BusinessException {

    private EntityNotExist(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static EntityNotExist build(String message) {
        return new EntityNotExist(Response.Status.NOT_FOUND, "EntityNotExist", message);
    }

    public static EntityNotExist build(String code, String message) {
        return new EntityNotExist(Response.Status.NOT_FOUND, code, message);
    }

    public static EntityNotExist build(Response.Status status, String code, String message) {
        return new EntityNotExist(status, code, message);
    }

}
