package org.athena.business;

import org.athena.api.AthenaFile;
import org.athena.db.FileRepository;
import org.athena.dto.FileDTO;
import org.athena.dto.Page;

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
    public Page<FileDTO> findAll(Integer page, Integer size) {
        List<AthenaFile> files = fileRepository.findAll();
        return Page.of(files.stream().map(x -> FileDTO.builder().build())
                .collect(Collectors.toList()), page, size, 120L);
    }

}
