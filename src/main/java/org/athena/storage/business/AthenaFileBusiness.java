package org.athena.storage.business;

import com.google.inject.Singleton;
import org.athena.common.exception.EntityNotExistException;
import org.athena.storage.db.AthenaFileRepository;
import org.athena.storage.db.StoreSpacesRepository;
import org.athena.storage.entity.AthenaFile;
import org.athena.storage.entity.StoreSpace;
import org.athena.storage.params.CreateFileParams;
import org.athena.storage.resp.FileResp;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 文件业务处理类
 */
@Singleton
public class AthenaFileBusiness {

    private static final Logger logger = LoggerFactory.getLogger(AthenaFileBusiness.class);

    @Inject
    private AthenaFileRepository athenaFileRepository;

    @Inject
    private StoreSpacesRepository storeSpacesRepository;

    /**
     * 获取所有文件列表
     */
    public List<FileResp> findAll(Integer page, Integer size) {
        logger.info("page: {}, size: {}", page, size);
        List<AthenaFile> files = athenaFileRepository.findAll();
        return files.stream().map(x -> FileResp.builder().build())
                .collect(Collectors.toList());
    }

    /**
     * 创建文件元数据
     *
     * @param params 创建文件参数
     */
    @InTransaction(value = TransactionIsolationLevel.REPEATABLE_READ)
    public void createFile(CreateFileParams params) {
        Optional<StoreSpace> optional = storeSpacesRepository.findByStoreSpaceId(params.getStoreId());
        if (!optional.isPresent()) {
            throw EntityNotExistException.build("存储空间不存在");
        }
    }

}
