package org.athena.api.resource;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.athena.api.business.FileBusiness;
import org.athena.api.params.CreateFileParams;
import org.athena.api.resp.FileResp;
import org.athena.common.resp.Response;
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
@Api(tags = "B")
public class FileResource {

    private FileBusiness fileBusiness;

    public FileResource(FileBusiness fileBusiness) {
        this.fileBusiness = fileBusiness;
    }

    @Timed
    @GET
    @ApiOperation(value = "文件列表", notes = "获取文件列表信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "获取文件列表成功")
    })
    public Response<List<FileResp>> findAll(
            @ApiParam(value = "分页页码") @QueryParam("page") Integer page,
            @ApiParam(value = "每页数量") @QueryParam("size") Integer size) {
        return Response.build(fileBusiness.findAll(page, size));
    }

    @Timed
    @GET
    @ApiOperation(value = "新建文件", notes = "新建文件元数据信息")
    @ApiResponses({
            @ApiResponse(code = 201, message = "新建成功")
    })
    public Response<List<FileResp>> create(@Valid CreateFileParams createFileParams) {
        fileBusiness.createFile(createFileParams);
        return Response.build();
    }

    @Timed
    @POST
    @ApiOperation(value = "文件列表", notes = "获取文件列表信息")
    public Response<List<FileResp>> upload(final FormDataMultiPart multiPart) {
        return null;
    }

}
