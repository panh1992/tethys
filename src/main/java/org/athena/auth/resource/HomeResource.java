package org.athena.auth.resource;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.auth.business.UserBusiness;
import org.athena.auth.entity.Role;
import org.athena.auth.entity.User;
import org.athena.auth.params.LoginParams;
import org.athena.auth.params.RegisterParams;
import org.athena.common.resp.Result;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Timed
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "A 通用资源")
public class HomeResource {

    @Inject
    private UserBusiness userBusiness;

    /**
     * 用户注册
     */
    @POST
    @Path("register")
    @ApiOperation(value = "注册", notes = "用户使用 用户名、邮箱、密码 注册")
    @ApiResponses({
            @ApiResponse(code = 200, message = "注册成功")
    })
    public Result register(@Valid RegisterParams params) {
        userBusiness.register(params.getUserName(), params.getPassWord());
        return Result.build();
    }

    /**
     * 用户登录
     */
    @POST
    @Path("login")
    @ApiOperation(value = "登录", notes = "用户使用 用户名或邮箱、密码 进行登录操作")
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录成功"),
            @ApiResponse(code = 404, message = "用户名或密码错误")
    })
    public Result<String> login(@Valid LoginParams params) {
        return Result.build(userBusiness.login(params.getUserName(), params.getPassWord()), "登录成功");
    }

    @GET
    @Path("roles")
    public Result<List<Role>> login() {
        return Result.build(userBusiness.findAllRole(), "登录成功");
    }

    @GET
    @Path("users")
    public Result<List<User>> users() {
        return Result.build(userBusiness.findAllUser(), "登录成功");
    }

}
