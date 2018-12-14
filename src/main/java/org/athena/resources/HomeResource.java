package org.athena.resources;

import com.codahale.metrics.annotation.Timed;
import org.athena.business.UserBusiness;
import org.athena.dto.Response;
import org.athena.dto.UserDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HomeResource {

    private UserBusiness userBusiness;

    public HomeResource(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    /**
     * 用户注册
     */
    @Timed
    @POST
    @Path("register")
    public Response register(UserDTO userDTO) {
        userBusiness.register(userDTO);
        return Response.build();
    }

    /**
     * 用户登录
     */
    @Timed
    @POST
    @Path("login")
    public Response<String> login(UserDTO userDTO) {
        return Response.build(userBusiness.login(userDTO), "登录成功");
    }

    @Timed
    @GET
    @Path("list")
    public Response<List<UserDTO>> findAll() {
        return Response.build(userBusiness.findAll());
    }

    @Timed
    @GET
    @Path("test")
    public Integer test() {
        return userBusiness.test();
    }

}
