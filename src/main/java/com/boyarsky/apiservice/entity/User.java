package com.boyarsky.apiservice.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@NamedQuery(name = "getUserByEmail", query = "SELECT user FROM User user WHERE user.email = :email")
public class User implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
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

    @Column(updatable = false, nullable = false, name = "creation_time")
    private OffsetDateTime creationTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "picture_url")
    private String pictureUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public User(String name, String email, Boolean isActive) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.auth = AuthType.BASIC;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", auth=" + auth +
                ", email='" + email + '\'' +
                ", creationTime=" + creationTime +
                ", isActive=" + isActive +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", roles=" + roles +
                '}';
    }

    public User(String email, Set<Role> roles) {
        this(email, email, true);
        this.roles = roles;
    }

    public User(String password, String email, Set<Role> roles) {
        this(email, roles);
        this.password = password;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public User(String name, String password, AuthType auth, @Email String email, OffsetDateTime creationTime, Boolean isActive, String pictureUrl, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.auth = auth;
        this.email = email;
        this.creationTime = creationTime;
        this.isActive = isActive;
        this.pictureUrl = pictureUrl;
        this.roles = roles;
    }

    public User() {
    }

    @PrePersist
    public void preInsert() {
        if (this.creationTime == null) {
            this.creationTime = OffsetDateTime.now();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthType getAuth() {
        return auth;
    }

    public void setAuth(AuthType auth) {
        this.auth = auth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @JsonGetter(value = "roles")
    public Set<String> getStringRoles() {
        return roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
