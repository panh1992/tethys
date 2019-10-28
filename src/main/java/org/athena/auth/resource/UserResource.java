package org.athena.auth.resource;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.auth.business.UserBusiness;
import org.athena.auth.resp.UserResp;
import org.athena.common.resp.Result;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Timed
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "U 用户资源")
public class UserResource {

    @Inject
    private UserBusiness userBusiness;

    /**
     * 获取用户详情
     */
    @GET
    @Path("/{user_id}")
    @ApiOperation(value = "获取用户信息", notes = "获取用户详情")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取用户信息成功", response = Result.class),
            @ApiResponse(code = 404, message = "不存在此用户")
    })
    public Result<UserResp> get(@PathParam("user_id") Long userId) {
        return Result.build(userBusiness.get(userId));
    }

}
