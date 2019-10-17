package org.athena.storage.business;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.athena.common.exception.EntityAlreadyExistsException;
import org.athena.common.resp.PageResp;
import org.athena.common.util.QueryUtil;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.storage.db.StoreSpacesRepository;
import org.athena.storage.entity.StoreSpaces;
import org.athena.storage.resp.StoreSpacesResp;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        StoreSpaces storeSpaces = StoreSpaces.builder().creatorId(userId).storeSpace(name).description(description)
                .storeSize(0L).deleted(Boolean.FALSE).storeSpacesId(idWorker.nextId())
                .createTime(Instant.now()).build();
        storeSpacesRepository.save(storeSpaces);
    }

    /**
     * 获取存储空间
     */
    @InTransaction(readOnly = true)
    public PageResp find(Long userId, String name, Long limit, Long offset) {
        List<StoreSpaces> list = storeSpacesRepository.findByCreatorIdAndNameLike(userId, QueryUtil.like(name),
                limit, offset);
        if (list.isEmpty()) {
            return PageResp.of(Lists.newArrayList(), limit, offset);
        }
        long total = storeSpacesRepository.countByCreatorIdAndNameLike(userId, QueryUtil.like(name));
        return PageResp.of(list.stream().map(x -> StoreSpacesResp.builder().storeSpacesId(x.getStoreSpacesId())
                        .storeSpace(x.getStoreSpace()).storeSize(x.getStoreSize()).creatorId(x.getCreatorId())
                        .description(x.getDescription()).deleted(x.getDeleted()).createTime(x.getCreateTime())
                        .modifyTime(x.getModifyTime()).build()).collect(Collectors.toList()),
                limit, offset, total);
    }

}
