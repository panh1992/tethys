package org.athena.storage.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.common.resp.Result;
import org.athena.common.util.QueryUtil;
import org.athena.common.util.SystemContext;
import org.athena.storage.business.AthenaFileBusiness;
import org.athena.storage.params.CreateFileParams;
import org.athena.storage.resp.FileInfoResp;
import org.athena.storage.resp.FileResp;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 文件资源 处理文件元数据
 */
@Timed
@Singleton
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "F 文件服务")
public class FileResource {

    @Inject
    private AthenaFileBusiness athenaFileBusiness;

    @ApiOperation(value = "文件列表", notes = "获取 某存储空间，某文件夹 下级文件列表信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取文件列表成功", response = FileResp.class, responseContainer = "List")
    })
    @GET
    public Response findNextAll(
            @ApiParam(value = "存储空间主键, 不传取默认存储空间") @QueryParam("store_space_id") Long storeSpaceId,
            @ApiParam(value = "文件主键, 不传取存储空间根文件") @QueryParam("file_id") Long fileId,
            @ApiParam(value = "限制几条记录", required = true) @QueryParam("limit") Long limit,
            @ApiParam(value = "从第几条记录开始", required = true) @QueryParam("offset") Long offset) {
        return Response.ok(Result.build(athenaFileBusiness.findNextAll(SystemContext.getUserId(), storeSpaceId, fileId,
                QueryUtil.limit(limit), QueryUtil.offset(offset)))).build();
    }

    @ApiOperation(value = "获取文件信息", notes = "获取文件详细信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取文件列表成功", response = FileInfoResp.class)
    })
    @GET
    @Path("/{file_id}")
    public Response get(@ApiParam("文件主键") @PathParam("file_id") Long fileId) {
        return Response.ok(Result.build(athenaFileBusiness.get(SystemContext.getUserId(), fileId))).build();
    }

    /**
     * 新建文件元数据信息
     */
    @ApiOperation(value = "新建文件", notes = "新建文件元数据信息")
    @ApiResponses({
            @ApiResponse(code = 201, message = "新建成功")
    })
    @POST
    public Response create(@Valid CreateFileParams params) {
        athenaFileBusiness.create(params.getStoreSpaceId(), params.getFilePath(), params.getIsDir(),
                params.getDescription());
        return Response.status(Response.Status.CREATED).entity(Result.build()).build();
    }

    /**
     * 新建文件元数据信息
     */
    @ApiOperation(value = "新建文件", notes = "新建文件元数据信息")
    @ApiResponses({
            @ApiResponse(code = 201, message = "新建成功")
    })
    @PATCH
    @Path("/{file_id}")
    public Response move(@ApiParam("文件主键") @PathParam("file_id") Long fileId,
                         @ApiParam("文件主键") @QueryParam("file_id") Long fileDirId) {
        athenaFileBusiness.move(SystemContext.getUserId(), fileId, fileDirId);
        return Response.status(Response.Status.CREATED).entity(Result.build()).build();
    }

    @ApiOperation(value = "文件列表", notes = "获取文件列表信息")
    @POST
    @Path("/upload")
    public Result<Void> upload(final FormDataMultiPart multiPart) {
        return null;
    }

}
