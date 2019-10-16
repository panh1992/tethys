package org.athena.storage.db;

import org.athena.storage.entity.StoreSpaces;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;

import java.util.Optional;

@JdbiRepository
public interface StoreSpacesRepository {

    @SqlQuery("SELECT * FROM store_spaces WHERE store_spaces_id = :store_spaces_id")
    Optional<StoreSpaces> findByStoreSpacesId(@Bind("store_spaces_id") Long storeId);

    @SqlQuery("SELECT * FROM store_spaces WHERE store_space = :store_spaces")
    Optional<StoreSpaces> findByStoreSpace(@Bind("store_spaces") String storeSpaces);

    @SqlUpdate("INSERT INTO store_spaces (store_spaces_id, creater_id, store_space, store_size, is_deleted, "
            + "create_time, modify_time, description) values (:storeSpacesId, :createrId, :storeSpace, :storeSize, "
            + ":deleted, :createTime, :modifyTime, :description)")
    void save(@BindBean StoreSpaces storeSpaces);

}
