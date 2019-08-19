package org.athena.guice.repository;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import org.athena.storage.db.FileRepository;
import org.jdbi.v3.core.Jdbi;

public class StorageRepositoryModule extends AbstractModule {

    @Inject
    @Provides
    public FileRepository fileRepository(Jdbi jdbi) {
        return jdbi.onDemand(FileRepository.class);
    }

}
