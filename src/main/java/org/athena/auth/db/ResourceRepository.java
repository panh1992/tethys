package org.athena.auth.db;

import org.athena.auth.entity.Resource;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;

@JdbiRepository
@InTransaction
public interface ResourceRepository {

    @SqlQuery("SELECT * FROM resource")
    List<Resource> findAll();

}
