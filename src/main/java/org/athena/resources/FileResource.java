package org.athena.resources;

import com.codahale.metrics.annotation.Timed;
import org.athena.api.AthenaFile;
import org.athena.business.FileBusiness;
import org.athena.dto.Response;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
    public Response<List<AthenaFile>> findAll() {
        return Response.build(fileBusiness.findAll());
    }

}
