package org.athena.config.authenticate;

import io.dropwizard.auth.UnauthorizedHandler;
import org.athena.dto.ErrorDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UnAuthorizedResourceHandler implements UnauthorizedHandler {

    @Override
    public Response buildResponse(String prefix, String realm) {

        ErrorDTO errorDTO = ErrorDTO.builder().code(Response.Status.UNAUTHORIZED.toString())
                .message("认证失败！").build();
        return Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorDTO).build();

    }

}
