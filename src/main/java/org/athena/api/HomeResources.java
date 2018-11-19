package org.athena.api;

import com.google.common.collect.Maps;
import org.athena.jdbi.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {

    private UserRepository userRepository;

    public HomeResources(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    public Map<String, Object> index() throws IOException {
        Map<String, Object> map = Maps.newHashMap();
        map.put("修改前", "sdfghjk");
        userRepository.testUser();
        throw new IOException("asdfg");
    }

}
