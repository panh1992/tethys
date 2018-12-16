package org.athena.db;

import org.athena.api.AthenaFile;

import java.util.List;

public interface FileRepository {

    List<AthenaFile> findAll();

}
