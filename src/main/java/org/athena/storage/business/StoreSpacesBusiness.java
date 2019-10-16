package org.athena.storage.business;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.athena.common.exception.EntityAlreadyExistsException;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.storage.db.StoreSpacesRepository;
import org.athena.storage.entity.StoreSpaces;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.time.Instant;
import java.util.Optional;

/**
 * 存储空间业务处理类
 */
@Singleton
public class StoreSpacesBusiness {

    @Inject
    private SnowflakeIdWorker idWorker;

    @Inject
    private StoreSpacesRepository storeSpacesRepository;

    /**
     * 创建存储空间
     */
    @InTransaction(TransactionIsolationLevel.REPEATABLE_READ)
    public void createSpace(Long userId, String name, String description) {
        Optional<StoreSpaces> optional = storeSpacesRepository.findByStoreSpace(name);
        if (optional.isPresent()) {
            throw EntityAlreadyExistsException.build("存储空间已存在，请重试");
        }
        StoreSpaces storeSpaces = StoreSpaces.builder().createrId(userId).storeSpace(name).description(description)
                .storeSize(0L).deleted(Boolean.FALSE).storeSpacesId(idWorker.nextId())
                .createTime(Instant.now()).build();
        storeSpacesRepository.save(storeSpaces);
    }

}
