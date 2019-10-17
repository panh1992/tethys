package org.athena.storage.db.queries;

import com.google.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;

@JdbiRepository
public abstract class StoreSpacesQueries {

    @Inject
    private Jdbi jdbi;

}
