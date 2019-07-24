package org.athena.storage.business;

import lombok.extern.slf4j.Slf4j;
import org.athena.storage.db.FileRepository;
import org.athena.storage.entity.AthenaFile;
import org.athena.storage.params.CreateFileParams;
import org.athena.storage.resp.FileResp;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件业务处理类
 */
@Slf4j
public class FileBusiness {

    private FileRepository fileRepository;

    public FileBusiness(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * 获取所有文件列表
     */
    public List<FileResp> findAll(Integer page, Integer size) {
        log.info("sks:" + page + size);
        List<AthenaFile> files = fileRepository.findAll();
        return files.stream().map(x -> FileResp.builder().build())
                .collect(Collectors.toList());
    }

    /**
     * 创建文件元数据
     *
     * @param createFileParams 创建文件参数
     */
    public void createFile(CreateFileParams createFileParams) {
        log.info("createFile:" + createFileParams);
    }

}
