package org.athena.auth.db;

import org.athena.auth.entity.User;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;
import java.util.Optional;

@JdbiRepository
@InTransaction
public interface UserRepository {

    @SqlQuery("SELECT id, username, nickname, password, email, mobile, profile, create_time FROM auth.user "
            + "ORDER BY create_time DESC")
    List<User> findAll();

    @SqlQuery("SELECT id, username, nickname, password, email, mobile, profile, create_time FROM auth.user "
            + "WHERE username = :username")
    Optional<User> findByUserName(@Bind("username") String userName);

    @SqlUpdate("INSERT INTO auth.user (id, username, nickname, password, email, mobile, profile, create_time) VALUES "
            + "(:id, :userName, :nickName, :passWord, :email, :mobile, :profile, :createTime)")
    void save(@BindBean User user);

    @SqlUpdate("UPDATE auth.user SET username = 'asdfg'")
    void updateUser();

}
