package org.athena.jdbi;

import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.io.IOException;
import java.util.List;

public interface UserRepository {

    @SqlQuery("select username from users")
    List<String> findName();

    @SqlUpdate("update users set username = 'asdfg'")
    void updateUser();

    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    default void testUser() throws IOException {

        updateUser();

        System.out.println(findName());

        throw new IOException("woof");
    }

}
