package org.athena.storage.db;

import org.athena.storage.entity.StoreSpaces;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;

import java.util.List;
import java.util.Optional;

@JdbiRepository
public interface StoreSpacesRepository {

    @SqlQuery("SELECT * FROM store_spaces WHERE store_spaces_id = :store_spaces_id")
    Optional<StoreSpaces> findByStoreSpacesId(@Bind("store_spaces_id") Long storeId);

    @SqlQuery("SELECT * FROM store_spaces WHERE store_space = :store_space")
    Optional<StoreSpaces> findByStoreSpace(@Bind("store_space") String storeSpace);

    @SqlQuery("SELECT * FROM store_spaces WHERE creator_id = :creator_id AND store_space LIKE :store_space "
            + "limit :limit offset :offset")
    List<StoreSpaces> findByCreatorIdAndNameLike(@Bind("creator_id") Long userId, @Bind("store_space") String name,
                                                 @Bind("limit") Long limit, @Bind("offset") Long offset);

    @SqlQuery("SELECT COUNT(1) FROM store_spaces WHERE creator_id = :creator_id AND store_space LIKE :store_space")
    long countByCreatorIdAndNameLike(@Bind("creator_id") Long userId, @Bind("store_space") String name);

    @SqlUpdate("INSERT INTO store_spaces (store_spaces_id, creator_id, store_space, store_size, is_deleted, "
            + "create_time, modify_time, description) VALUES (:storeSpacesId, :createrId, :storeSpace, :storeSize, "
            + ":deleted, :createTime, :modifyTime, :description)")
    void save(@BindBean StoreSpaces storeSpaces);

}
