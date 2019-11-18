package org.athena.auth.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import org.athena.auth.business.RoleBusiness;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Timed
@Singleton
@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "R 权限资源")
public class RoleResource {

    @Inject
    private RoleBusiness roleBusiness;

    private void findAllRole(){

    }

}
