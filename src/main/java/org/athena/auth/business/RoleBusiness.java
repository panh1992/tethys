package org.athena.auth.business;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.athena.auth.db.RoleRepository;
import org.athena.auth.entity.Role;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;

@Singleton
public class RoleBusiness {

    private static final Logger logger = LoggerFactory.getLogger(RoleBusiness.class);

    @Inject
    private RoleRepository roleRepository;

    @InTransaction(value = TransactionIsolationLevel.REPEATABLE_READ, readOnly = true)
    private void findAllRole() {
        List<Role> roles = roleRepository.findAll();


    }

}
