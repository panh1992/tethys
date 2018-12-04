package org.athena.api;

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
 * 用户视角文件元数据
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class Files {

    /**
     * 文件主键
     */
    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 存储空间
     */
    @Column(name = "store_id", length = 32)
    private String storeId;

    /**
     * 创建用户
     */
    @Column(name = "creater_id", length = 32)
    private String createrId;

    /**
     * 文件所属存储空间名称
     */
    @Column(name = "store_space", length = 128)
    private String storeSpace;

    /**
     * 文件名
     */
    @Column(name = "file_name", length = 255)
    private String fileName;

    /**
     * 用户角度文件大小   单位 byte
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 存储来源
     */
    @Column(name = "source_id", length = 32)
    private String sourceId;

    /**
     * 存储来源类型  upload   mount   system    task
     */
    @Column(name = "source_type")
    private Integer sourceType;

    /**
     * 是否为目录
     */
    @Column(name = "is_dir")
    private Boolean isDir;

    /**
     * 文件的校验码
     */
    @Column(name = "check_sum", length = 255)
    private String checkSum;

    /**
     * 文件格式      以文件扩展名为依据
     */
    @Column(name = "format", length = 32)
    private String format;

    /**
     * 文件状态      new   uploading   available   failed   deleted
     */
    @Column(name = "status", length = 32)
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Instant modifyTime;

    /**
     * 描述信息
     */
    @Column(name = "description", length = 255)
    private String description;

}

