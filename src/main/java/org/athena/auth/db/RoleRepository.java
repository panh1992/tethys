package org.athena.auth.db;

import org.athena.auth.entity.Role;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;

import java.util.List;

@JdbiRepository
public interface RoleRepository {

    @SqlQuery("SELECT id, name, create_time, modify_time, description FROM auth.role")
    List<Role> findAll();

}
