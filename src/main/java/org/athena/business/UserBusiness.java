package org.athena.business;

import org.athena.api.User;
import org.athena.db.UserRepository;
import org.athena.dto.UserDTO;
import org.athena.exceptions.EntityAlreadyExists;
import org.athena.exceptions.EntityNotExist;
import org.athena.utils.CryptoUtil;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserBusiness {

    private UserRepository userRepository;

    public UserBusiness(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 用户注册
     */
    public void register(UserDTO userDTO) {
        User checkUser = userRepository.findByUserName(userDTO.getUserName());
        if (Objects.isNull(checkUser)) {
            throw EntityAlreadyExists.build("用户名已存在");
        }
        User user = User.builder().id(CryptoUtil.getUUID()).userName(userDTO.getUserName()).email(userDTO.getEmail())
                .mobile(userDTO.getMobile()).passWord(CryptoUtil.hashpw(userDTO.getPassWord()))
                .createTime(Instant.now()).build();
        userRepository.save(user);
    }

    /**
     * 用户登录
     */
    public void login(UserDTO userDTO) {
        User user = userRepository.findByUserName(userDTO.getUserName());
        if (Objects.isNull(user) || CryptoUtil.checkpw(userDTO.getPassWord(), user.getPassWord())) {
            throw EntityNotExist.build("用户名或密码错误");
        }
    }

    /**
     * 查询所有用户
     */
    public List<UserDTO> findAll() {
        return userRepository.findAll().parallelStream().map(user -> UserDTO.builder().userId(user.getId())
                .userName(user.getUserName()).email(user.getEmail()).mobile(user.getMobile())
                .passWord(user.getPassWord()).createTime(user.getCreateTime()).build())
                .collect(Collectors.toList());
    }

    public Integer test() {
        userRepository.testUser();
        return 1234;
    }
}
