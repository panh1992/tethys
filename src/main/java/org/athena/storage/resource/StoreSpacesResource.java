package org.athena.storage.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.common.resp.Response;
import org.athena.common.util.SystemContext;
import org.athena.storage.business.StoreSpacesBusiness;
import org.athena.storage.params.CreateSpaceParams;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public Response<Void> create(@Valid CreateSpaceParams params) {
        storeSpacesBusiness.createSpace(SystemContext.getUserId(), params.getName(), params.getDescription());
        return Response.build();
    }

}
