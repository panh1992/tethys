package org.athena.storage.params;

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
@ApiModel(description = "新建/修改 存储空间参数")
public class SpaceParams {

    @ApiModelProperty("存储空间名称")
    private String name;

    @ApiModelProperty("描述信息")
    private String description;

}