package org.athena.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileDTO {

    @JsonProperty("file_id")
    private String fileId;

    /**
     * 文件所属存储空间名称
     */
    @JsonProperty("store_space")
    private String storeSpace;

    /**
     * 文件名
     */
    @JsonProperty("file_name")
    private String fileName;

    /**
     * 用户角度文件大小   单位 byte
     */
    @JsonProperty("file_size")
    private Long fileSize;

    /**
     * 存储来源
     */
    @JsonProperty("source_id")
    private String sourceId;

    /**
     * 存储来源类型  upload   mount   system    task
     */
    @JsonProperty("source_type")
    private String sourceType;

    /**
     * 是否为目录
     */
    @JsonProperty("is_dir")
    private Boolean isDir;

    /**
     * 文件的校验码
     */
    @JsonProperty("check_sum")
    private String checkSum;

    /**
     * 文件格式      以文件扩展名为依据
     */
    @JsonProperty("format")
    private String format;

    /**
     * 文件状态      new   uploading   available   failed   deleted
     */
    @JsonProperty("status")
    private String status;

    /**
     * 创建时间
     */
    @JsonProperty("create_time")
    private Instant createTime;

    /**
     * 修改时间
     */
    @JsonProperty("modify_time")
    private Instant modifyTime;

    /**
     * 描述信息
     */
    @JsonProperty("description")
    private String description;

}
