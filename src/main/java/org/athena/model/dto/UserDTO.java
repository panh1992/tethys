package org.athena.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDTO {

    /**
     * 用户名称
     */
    @JsonProperty("username")
    private String userName;

    /**
     * 用户昵称
     */
    @JsonProperty("nick_name")
    private String nickName;

    /**
     * 用户密码
     */
    @JsonProperty("password")
    private String passWord;

    /**
     * 邮箱
     */
    @JsonProperty("email")
    private String email;

    /**
     * 手机号
     */
    @JsonProperty("mobile")
    private String mobile;

    /**
     * 个人简介
     */
    @JsonProperty("profile")
    private String profile;

}
