package org.athena.business;

import org.athena.api.AthenaFile;
import org.athena.common.resp.FileResp;
import org.athena.common.resp.PageResp;
import org.athena.db.FileRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件业务处理类
 */
public class FileBusiness {

    private FileRepository fileRepository;

    public FileBusiness(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * 获取所有文件列表
     */
    public PageResp<FileResp> findAll(Integer page, Integer size) {
        List<AthenaFile> files = fileRepository.findAll();
        return PageResp.of(files.stream().map(x -> FileResp.builder().build())
                .collect(Collectors.toList()), page, size, 120L);
    }

}
