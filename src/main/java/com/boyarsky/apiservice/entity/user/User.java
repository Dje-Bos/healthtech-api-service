package com.boyarsky.apiservice.entity.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth")
    private AuthType auth;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @CreatedDate
    private LocalDateTime creationTime;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private Boolean isActive;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "users_roles_user_id_fk"), inverseForeignKey = @ForeignKey(name = "users_roles_role_id_fk"))
    private Set<Role> roles;

    public User() {
    }

    public User(String email, Set<Role> roles) {
        this(email, email, true);
        this.roles = roles;
    }

    public User(String name, String email, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.auth = AuthType.BASIC;
    }

    public User(String password, String email, Set<Role> roles) {
        this(email, roles);
        this.password = password;
    }

    public User(String name, String password, AuthType auth, String email, LocalDateTime creationTime, Boolean isActive, String pictureUrl, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.auth = auth;
        this.email = email;
        this.creationTime = creationTime;
        this.isActive = isActive;
        this.pictureUrl = pictureUrl;
        this.roles = roles;
    }
}
