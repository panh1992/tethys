package org.athena.resources;

import com.google.common.collect.Maps;
import org.athena.api.User;
import org.athena.business.UserBusiness;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResource {

    private UserBusiness userBusiness;

    public HomeResource(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    /**
     * 测试事务
     */
    @GET
    public Map<String, Object> index() throws IOException {
        userBusiness.testUser();
        Map<String, Object> result = Maps.newHashMap();
        result.put("test", "test");
        return result;
    }

    @GET
    @Path("/list")
    public List<User> findAll() {
        return userBusiness.findAll();
    }

}
