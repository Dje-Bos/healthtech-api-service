package com.boyarsky.apiservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.Constraint;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "uid", updatable = false, unique = true)
    private String uid;

    private String description;

    @CreatedDate
    private LocalDateTime creationTime;

    public Role() {
    }

    public Role(String uid) {
        this.uid = uid;
    }

    public Role(String uid, String description) {
        this(uid);
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return getUid();
    }
}
