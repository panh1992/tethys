package org.athena.business;

import org.athena.api.User;
import org.athena.db.UserRepository;
import org.athena.dto.UserDTO;
import org.athena.utils.CryptoUtil;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class UserBusiness {

    private UserRepository userRepository;

    public UserBusiness(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserDTO userDTO) {
        User user = User.builder().id(CryptoUtil.getUUID()).userName(userDTO.getUserName()).email(userDTO.getEmail())
                .mobile(userDTO.getMobile()).passWord(CryptoUtil.hashpw(userDTO.getPassWord()))
                .createTime(Instant.now()).build();
        userRepository.save(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(user -> UserDTO.builder().userId(user.getId())
                .userName(user.getUserName()).email(user.getEmail()).mobile(user.getMobile())
                .passWord(CryptoUtil.hashpw(user.getPassWord())).createTime(user.getCreateTime()).build())
                .collect(Collectors.toList());
    }

    public Integer test() {
        userRepository.testUser();
        return 1234;
    }

}
