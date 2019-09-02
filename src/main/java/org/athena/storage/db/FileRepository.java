package org.athena.storage.db;

import org.athena.storage.entity.AthenaFile;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;

@JdbiRepository
@InTransaction
public interface FileRepository {

    @SqlQuery("SELECT * FROM files")
    List<AthenaFile> findAll();

}
