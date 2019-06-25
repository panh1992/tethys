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
@ApiModel(description = "新建文件元数据参数")
public class CreateFileParams {

    @ApiModelProperty("文件路径")
    private String filePath;

    @ApiModelProperty("是否为目录")
    private Boolean isDir;

    @ApiModelProperty("描述信息")
    private String description;

}
