package org.athena.storage.business;

import com.google.inject.Singleton;
import org.athena.storage.db.FileRepository;
import org.athena.storage.entity.AthenaFile;
import org.athena.storage.params.CreateFileParams;
import org.athena.storage.resp.FileResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件业务处理类
 */
@Singleton
public class FileBusiness {

    private static final Logger logger = LoggerFactory.getLogger(FileBusiness.class);

    @Inject
    private FileRepository fileRepository;

    /**
     * 获取所有文件列表
     */
    public List<FileResp> findAll(Integer page, Integer size) {
        logger.info("sks:" + page + size);
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
        logger.info("createFile:" + createFileParams);
    }

}
