package org.athena.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件树形关系 （采用闭包表）
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "path_tree")
public class PathTree {

    /**
     * 文件关系主键
     */
    @Id
    @Column(name = "id", length = 32)
    private String id;

    /**
     * 祖先文件
     */
    @Column(name = "ancestor_id", length = 32)
    private String ancestorId;

    /**
     * 子孙文件
     */
    @Column(name = "descendant_id", length = 32)
    private String descendantId;

    /**
     * 相对层级
     */
    @Column(name = "depth")
    private Boolean depth;

}
