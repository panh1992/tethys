package org.athena.api;

import org.athena.entity.User;
import org.athena.jdbi.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HomeResources {

    private UserRepository userRepository;

    public HomeResources(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 测试事务
     */
    @GET
    public Map<String, Object> index() throws IOException {
        userRepository.testUser();
        throw new IOException("asdfg");
    }

    @GET
    @Path("/list")
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
