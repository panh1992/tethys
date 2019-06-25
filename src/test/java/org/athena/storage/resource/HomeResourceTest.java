package org.athena.storage.resource;

import com.google.common.collect.Lists;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.athena.auth.business.UserBusiness;
import org.athena.auth.db.UserRepository;
import org.athena.auth.entity.User;
import org.athena.auth.params.LoginParams;
import org.athena.auth.params.RegisterParams;
import org.athena.auth.resource.HomeResource;
import org.athena.auth.resp.UserResp;
import org.athena.common.resp.Response;
import org.athena.common.util.Constant;
import org.athena.common.util.JWTUtil;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.common.util.crypto.CommonUtil;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class HomeResourceTest {

    private static final UserRepository userRepository = mock(UserRepository.class);

    private static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HomeResource(new UserBusiness(idWorker, userRepository)))
            .build();

    @Test
    public void register() {
        String userName = "panhong";
        String password = "pan123456..";
        RegisterParams params = RegisterParams.builder().userName(userName)
                .passWord(password).build();

        User user = User.builder().id(idWorker.nextId()).userName(userName).passWord(CommonUtil.hashpw(password))
                .createTime(Instant.now()).build();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        doThrow(new RuntimeException()).when(userRepository).save(user);

        Response response = resources.target("/register").request()
                .post(Entity.entity(params, MediaType.APPLICATION_JSON_TYPE), Response.class);

        assertEquals("SUCCESS", response.getCode());

    }

    @Test
    public void login() {
        User user = new User();
        long userId = idWorker.nextId();
        user.setId(userId);
        user.setUserName("panhong");
        user.setPassWord(CommonUtil.hashpw("pan123456.."));

        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));

        LoginParams params = LoginParams.builder().userName("panhong").passWord("pan123456..").build();

        Response<String> response = resources.target("/login").request()
                .post(Entity.entity(params, MediaType.APPLICATION_JSON_TYPE), Response.class);

        String subject = null;
        try {
            JwtClaims claims = JWTUtil.validation(response.getData(), Constant.AUTHORIZATION_DURATION);
            subject = claims.getSubject();
        } catch (InvalidJwtException | MalformedClaimException e) {
            e.printStackTrace();
        }

        assertEquals(user.getId().toString(), subject);

    }

    @Test
    public void findAll() {
        List<User> result = Lists.newArrayList();
        result.add(User.builder().mobile("122222").build());

        when(userRepository.findAll()).thenReturn(result);

        Response<List<UserResp>> response = resources.target("/list").request().get(Response.class);

        assertEquals(1, response.getData().size());

        verify(userRepository).findAll();
    }

}