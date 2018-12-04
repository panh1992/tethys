package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.athena.utils.Constant;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    /**
     * 用户Id
     */
    @JsonProperty("user_id")
    private String userId;

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

    /**
     * 创建时间
     */
    @JsonProperty("create_time")
    @JsonFormat(timezone = Constant.TIME_ZONE, pattern = Constant.DATE_FORMAT)
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant createTime;

}
