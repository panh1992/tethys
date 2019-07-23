package org.athena.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role {

    /**
     * 角色主键
     */
    @Id
    @Column(name = "id", length = 32)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "name", length = 64)
    private String name;

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
     * 角色描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 角色下权限信息
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resource_relation", schema = "public", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "resource_id", referencedColumnName = "id")})
    private List<Resource> resources;

}
