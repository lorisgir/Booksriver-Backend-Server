package com.ghibo.userserver.domain.models;

import lombok.Data;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_follow")
@Data
@FilterDef(
        name = "userIdFilter",
        parameters = @ParamDef(name = "uid", type = "long")
)
public class UserFollow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_followed_id", nullable = false)
    private User userFollowed;

    public UserFollow(){}

    public UserFollow(User user, User userFollowed) {
        this.user = user;
        this.userFollowed = userFollowed;
    }
}
