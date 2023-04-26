package com.ghibo.userserver.domain.models;

import lombok.Data;
import org.hibernate.annotations.Filter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "google_id")
    private String googleId;

    @OneToMany(mappedBy = "user")
    @Filter(
            name = "userIdFilter",
            condition = "user_id = :uid"
    )
    private List<UserFollow> userFollows;

    @OneToMany(mappedBy = "userFollowed")
    @Filter(
            name = "userIdFilter",
            condition = "user_followed_id = :uid"
    )
    private List<UserFollow> userFollowed;

    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
