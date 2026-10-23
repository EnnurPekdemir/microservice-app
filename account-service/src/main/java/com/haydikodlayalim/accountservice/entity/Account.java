package com.haydikodlayalim.accountservice.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
@Table(value = "accounts")
public class Account implements Serializable {

    @PrimaryKey
    private String id;

    @Column
    private String username;

    private String email;

    private String passwd;

    public Account() {
        this.id = UUID.randomUUID().toString();
    }

    public Account(String id, String username, String email, String passwd) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.passwd = passwd;
    }
}

