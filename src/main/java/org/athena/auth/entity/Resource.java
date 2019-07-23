package org.athena.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource")
public class Resource {

    /**
     * 资源主键
     */
    @Id
    @Column(name = "id", length = 32)
    private Long id;

    /**
     * 请求 URI
     */
    @Column(name = "uri", length = 64)
    private String uri;

    /**
     * 请求 Method
     */
    @Column(name = "method", length = 64)
    private String method;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Instant createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Instant modifyTime;

    /**
     * 资源标识
     */
    @Column(name = "permission", length = 128)
    private String permission;

    /**
     * 权限描述
     */
    @Column(name = "description")
    private String description;

}
