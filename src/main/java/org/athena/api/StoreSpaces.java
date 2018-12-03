package org.athena.api;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

/**
 * 存储空间
 */
@Data
@Entity
@Table(name = "store_spaces")
public class StoreSpaces {

    /**
     * 存储空间主键
     */
    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 创建用户
     */
    @Column(name = "creater_id", length = 32)
    private String createrId;

    /**
     * 数据存储空间
     */
    @Column(name = "store_space", length = 128)
    private String storeSpace;

    /**
     * 存储空间大小
     */
    @Column(name = "store_size")
    private Long storeSize;

    /**
     * 是否删除
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
