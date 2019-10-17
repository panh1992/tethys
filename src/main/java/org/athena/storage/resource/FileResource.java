package org.athena.storage.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.common.resp.Result;
import org.athena.storage.business.AthenaFileBusiness;
import org.athena.storage.params.CreateFileParams;
import org.athena.storage.resp.FileResp;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 文件资源 处理文件元数据
 */
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(tags = "F 文件服务")
public class FileResource {

    @Inject
    private AthenaFileBusiness athenaFileBusiness;

    @Timed
    @GET
    @ApiOperation(value = "文件列表", notes = "获取文件列表信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取文件列表成功")
    })
    public Result<List<FileResp>> findAll(
            @ApiParam(value = "分页页码") @QueryParam("page") Integer page,
            @ApiParam(value = "每页数量") @QueryParam("size") Integer size) {
        return Result.build(athenaFileBusiness.findAll(page, size));
    }

    @Timed
    @POST
    @ApiOperation(value = "新建文件", notes = "新建文件元数据信息")
    @ApiResponses({
            @ApiResponse(code = 201, message = "新建成功")
    })
    public Result<List<FileResp>> create(@Valid CreateFileParams createFileParams) {
        athenaFileBusiness.createFile(createFileParams);
        return Result.build();
    }

    @Timed
    @POST
    @Path("/upload")
    @ApiOperation(value = "文件列表", notes = "获取文件列表信息")
    public Result<List<FileResp>> upload(final FormDataMultiPart multiPart) {
        return null;
    }

}
