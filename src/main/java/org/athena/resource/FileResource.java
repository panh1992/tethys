package org.athena.resource;

import com.codahale.metrics.annotation.Timed;
import org.athena.business.FileBusiness;
import org.athena.dto.FileDTO;
import org.athena.dto.Page;
import org.athena.dto.Response;

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
public class FileResource {

    private FileBusiness fileBusiness;

    public FileResource(FileBusiness fileBusiness) {
        this.fileBusiness = fileBusiness;
    }

    @Timed
    @GET
    public Response<Page<FileDTO>> findAll(@QueryParam("page") Integer page, @QueryParam("size") Integer size) {
        return Response.build(fileBusiness.findAll(page, size));
    }

}
