package org.athena.auth.business;

import lombok.AllArgsConstructor;
import org.athena.auth.db.ResourceRepository;
import org.athena.auth.db.RoleRepository;
import org.athena.auth.db.UserRepository;
import org.athena.auth.entity.Role;
import org.athena.auth.entity.User;
import org.athena.common.exception.EntityAlreadyExistsException;
import org.athena.common.exception.EntityNotExistException;
import org.athena.common.exception.InternalServerError;
import org.athena.common.util.Constant;
import org.athena.common.util.JWTUtil;
import org.athena.common.util.SnowflakeIdWorker;
import org.athena.common.util.crypto.CommonUtil;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserBusiness {

    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    private SnowflakeIdWorker idWorker;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private ResourceRepository resourceRepository;

    /**
     * 注册用户
     *
     * @param userName 用户名
     * @param password 密码
     */
    public void register(String userName, String password) {
        Optional<User> checkUser = userRepository.findByUserName(userName);
        if (checkUser.isPresent()) {
            throw EntityAlreadyExistsException.build("用户名已存在");
        }
        User user = User.builder().id(idWorker.nextId()).userName(userName).passWord(CommonUtil.hashpw(password))
                .createTime(Instant.now()).build();
        userRepository.save(user);
    }

    /**
     * 用户登录
     *
     * @param userName 用户名
     * @param passWord 密码
     * @return jwt Token
     */
    public String login(String userName, String passWord) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (!userOptional.isPresent() || !CommonUtil.checkpw(passWord, userOptional.get().getPassWord())) {
            throw EntityNotExistException.build("用户名或密码错误");
        }
        try {
            return JWTUtil.createToken(userOptional.get().getId().toString(), Constant.AUTHORIZATION_DURATION);
        } catch (JoseException e) {
            logger.error("JWT WEB TOKEN 创建失败, 异常信息: ", e);
            throw InternalServerError.build("jwt token 创建失败");
        }
    }

    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }
}
