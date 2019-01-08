package org.athena.business;

import org.athena.api.User;
import org.athena.db.UserRepository;
import org.athena.dto.UserDTO;
import org.athena.exception.EntityAlreadyExistsException;
import org.athena.exception.EntityNotExistException;
import org.athena.exception.InternalServerError;
import org.athena.util.Constant;
import org.athena.util.crypto.CommonUtil;
import org.athena.util.JWTUtil;
import org.jose4j.lang.JoseException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
        Optional<User> checkUser = userRepository.findByUserName(userDTO.getUserName());
        if (checkUser.isPresent()) {
            throw EntityAlreadyExistsException.build("用户名已存在");
        }
        User user = User.builder().id(CommonUtil.getUUID()).userName(userDTO.getUserName()).email(userDTO.getEmail())
                .mobile(userDTO.getMobile()).passWord(CommonUtil.hashpw(userDTO.getPassWord()))
                .createTime(Instant.now()).build();
        userRepository.save(user);
    }

    /**
     * 用户登录
     */
    public String login(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUserName(userDTO.getUserName());
        if (!userOptional.isPresent() || !CommonUtil.checkpw(userDTO.getPassWord(), userOptional.get().getPassWord())) {
            throw EntityNotExistException.build("用户名或密码错误");
        }
        try {
            return JWTUtil.createToken(userOptional.get().getId(), Constant.AUTHORIZATION_DURATION);
        } catch (JoseException e) {
            throw InternalServerError.build("jwt token 创建失败");
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
