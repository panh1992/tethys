package org.athena.api.db;

import org.athena.api.entity.User;
import org.athena.common.exception.EntityAlreadyExistsException;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    @SqlQuery("SELECT id, username, nickname, password, email, mobile, profile, create_time FROM users "
            + "ORDER BY create_time DESC")
    List<User> findAll();

    @SqlQuery("SELECT id, username, nickname, password, email, mobile, profile, create_time FROM users "
            + "WHERE username = :username")
    Optional<User> findByUserName(@Bind("username") String userName);

    @SqlUpdate("INSERT INTO users (id, username, nickname, password, email, mobile, profile, create_time) VALUES "
            + "(:id, :userName, :nickName, :passWord, :email, :mobile, :profile, :createTime)")
    void save(@BindBean User user);

    @SqlUpdate("UPDATE users SET username = 'asdfg'")
    void updateUser();

    /**
     * 事务测试
     */
    @Transaction(TransactionIsolationLevel.READ_UNCOMMITTED)
    default void testUser() {

        updateUser();

        throw EntityAlreadyExistsException.build("woof");
    }

}
