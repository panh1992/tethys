package org.athena.api.entity;

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
 * 文件上传任务
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "upload_tasks")
public class UploadTasks {

    /**
     * 上传任务主键
     */
    @Id
    @Column(name = "id", length = 32)
    private Long id;

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
     * 数据上传任务的状态   start   success  failed
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Boolean deleted;

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
     * 修改时间
     */
    @Column(name = "finish_time")
    private Instant finishTime;

}
