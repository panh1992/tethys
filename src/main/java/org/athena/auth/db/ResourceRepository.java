package org.athena.auth.db;

import org.athena.auth.entity.Resource;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface ResourceRepository {

    @SqlQuery("SELECT * FROM resource")
    List<Resource> findAll();

}
