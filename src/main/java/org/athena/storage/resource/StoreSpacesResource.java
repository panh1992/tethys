package org.athena.storage.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.common.util.QueryUtil;
import org.athena.common.util.SystemContext;
import org.athena.storage.business.StoreSpacesBusiness;
import org.athena.storage.params.SpaceParams;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 文件资源 处理文件元数据
 */
@Path("/spaces")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "S 存储空间")
public class StoreSpacesResource {

    @Inject
    private StoreSpacesBusiness storeSpacesBusiness;

    @Timed
    @POST
    @ApiOperation(value = "新建存储空间", notes = "新建存储空间")
    @ApiResponses({
            @ApiResponse(code = 201, message = "新建成功")
    })
    public Response create(@Valid SpaceParams params) {
        storeSpacesBusiness.create(SystemContext.getUserId(), params.getName(), params.getDescription());
        return Response.status(Response.Status.CREATED).build();
    }

    @Timed
    @GET
    @ApiOperation(value = "查询存储空间", notes = "查询存储空间")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取存储空间成功")
    })
    public Response find(@ApiParam(value = "存储空间名称") @QueryParam("name") String name,
                         @ApiParam(value = "分页页码") @QueryParam("limit") Long limit,
                         @ApiParam(value = "每页数量") @QueryParam("offset") Long offset) {
        return Response.ok(storeSpacesBusiness.find(SystemContext.getUserId(), name, QueryUtil.limit(limit),
                QueryUtil.offset(offset))).build();
    }

    @Timed
    @PUT
    @Path("/{store_space_id}")
    @ApiOperation(value = "修改存储空间", notes = "修改存储空间")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功")
    })
    public Response update(@ApiParam(value = "存储空间主键") @PathParam("store_space_id") Long storeSpaceId,
                           @Valid SpaceParams params) {
        storeSpacesBusiness.update(SystemContext.getUserId(), storeSpaceId, params.getName(), params.getDescription());
        return Response.status(Response.Status.CREATED).build();
    }

}
