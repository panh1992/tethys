package org.athena.api;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @Column(name = "username", length = 128)
    private String userName;

    @Column(name = "password", length = 128)
    private String passWord;

}
