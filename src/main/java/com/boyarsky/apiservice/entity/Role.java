package com.boyarsky.apiservice.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "roles")
public class Role implements Serializable, GrantedAuthority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "uid", updatable = false, unique = true)
    private String uid;
    @Column(name = "description")
    private String description;
    @Column(name = "creation_time")
    private OffsetDateTime creationTime;

    public Role(String uid, String description) {
        this(uid);
        this.description = description;
    }

    public Role(String uid) {
        this.uid = uid;
    }

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @PrePersist
    public void preInsert() {
        if (creationTime == null) {
            creationTime = OffsetDateTime.now();
        }
    }

    @Override
    public String getAuthority() {
        return getUid();
    }
}
