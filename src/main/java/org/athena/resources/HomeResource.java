package org.athena.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.Maps;
import org.athena.business.UserBusiness;
import org.athena.dto.UserDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> register(UserDTO userDTO) {
        userBusiness.register(userDTO);
        Map<String, Object> result = Maps.newHashMap();
        result.put("test", "test");
        return result;
    }

    @Timed
    @GET
    @Path("/list")
    public List<UserDTO> findAll() {
        return userBusiness.findAll();
    }

    @Timed
    @GET
    @Path("/test")
    public Integer test() {
        return userBusiness.test();
    }

}
