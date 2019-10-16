package org.athena.storage.db;

import org.athena.storage.entity.AthenaFile;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;

import java.util.List;

@JdbiRepository
public interface AthenaFileRepository {

    @SqlQuery("SELECT * FROM files")
    List<AthenaFile> findAll();

}
