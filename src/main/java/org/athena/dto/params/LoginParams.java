package org.athena.dto.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "登录参数")
public class LoginParams {

    @ApiModelProperty(name = "userName", value = "用户名称")
    private String userName;

    @ApiModelProperty(name = "email", value = "邮箱", notes = "暂未支持")
    private String email;

    @ApiModelProperty(name = "mobile", value = "手机号", notes = "暂未支持")
    private String mobile;

    @ApiModelProperty(name = "passWord", value = "用户密码")
    private String passWord;

}
