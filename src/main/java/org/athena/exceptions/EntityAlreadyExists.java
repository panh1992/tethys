package org.athena.exceptions;

import lombok.Getter;

import javax.ws.rs.core.Response;

/**
 * 实体已存在异常
 */
@Getter
public class EntityAlreadyExists extends BusinessException {

    private EntityAlreadyExists(Response.Status status, String code, String message) {
        super(status, code, message);
    }

    public static EntityAlreadyExists build(String message) {
        return new EntityAlreadyExists(Response.Status.CONFLICT, "EntityAlreadyExists", message);
    }

    public static EntityAlreadyExists build(String code, String message) {
        return new EntityAlreadyExists(Response.Status.CONFLICT, code, message);
    }

    public static EntityAlreadyExists build(Response.Status status, String code, String message) {
        return new EntityAlreadyExists(status, code, message);
    }

}
