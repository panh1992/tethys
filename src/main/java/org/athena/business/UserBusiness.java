package org.athena.business;

import org.athena.api.User;
import org.athena.db.UserRepository;

import java.io.IOException;
import java.util.List;

public class UserBusiness {

    private UserRepository userRepository;

    public UserBusiness(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void testUser() throws IOException {
        userRepository.testUser();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
