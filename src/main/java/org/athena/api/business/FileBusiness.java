package org.athena.api.business;

import org.athena.api.db.FileRepository;
import org.athena.api.entity.AthenaFile;
import org.athena.api.params.CreateFileParams;
import org.athena.api.resp.FileResp;

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
    public List<FileResp> findAll(Integer page, Integer size) {
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



    }

}
