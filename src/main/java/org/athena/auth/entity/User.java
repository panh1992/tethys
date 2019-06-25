package org.athena.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * 用户
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    /**
     * 用户主键
     */
    @Id
    @Column(name = "id", length = 32)
    private Long id;

    /**
     * 用户名称
     */
    @Column(name = "username", length = 64)
    private String userName;

    /**
     * 用户昵称
     */
    @Column(name = "nickname", length = 64)
    private String nickName;

    /**
     * 用户密码
     */
    @Column(name = "password", length = 128)
    private String passWord;

    /**
     * 邮箱
     */
    @Column(name = "email", length = 128)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "mobile", length = 32)
    private String mobile;

    /**
     * 个人简介
     */
    @Column(name = "profile", length = 255)
    private String profile;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Instant createTime;

}
