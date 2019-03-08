package org.athena.resource;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.athena.business.FileBusiness;
import org.athena.common.resp.FileResp;
import org.athena.common.resp.PageResp;
import org.athena.common.resp.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
    public Response<PageResp<FileResp>> findAll(
            @ApiParam(value = "分页页码", defaultValue = "0", required = true) @QueryParam("page") Integer page,
            @ApiParam(value = "每页数量", defaultValue = "20", required = true) @QueryParam("size") Integer size) {
        return Response.build(fileBusiness.findAll(page, size));
    }

}
