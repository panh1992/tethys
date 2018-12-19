package org.athena.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import org.athena.utils.jackson.InstantDeserializer;
import org.athena.utils.jackson.InstantSerializer;

import java.time.Instant;

@Data
@Builder
public class FileDTO {

    /**
     * 文件主键
     */
    private String fileId;

    /**
     * 文件所属存储空间名称
     */
    private String storeSpace;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 用户角度文件大小   单位 byte
     */
    private Long fileSize;

    /**
     * 存储来源
     */
    private String sourceId;

    /**
     * 存储来源类型  upload   mount   system    task
     */
    private String sourceType;

    /**
     * 是否为目录
     */
    private Boolean isDir;

    /**
     * 文件的校验码
     */
    private String checkSum;

    /**
     * 文件格式      以文件扩展名为依据
     */
    private String format;

    /**
     * 文件状态      new   uploading   available   failed   deleted
     */
    private String status;

    /**
     * 创建时间
     */
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant modifyTime;

    /**
     * 描述信息
     */
    private String description;

}
