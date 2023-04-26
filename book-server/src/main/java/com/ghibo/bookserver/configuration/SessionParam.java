package com.ghibo.bookserver.configuration;

import lombok.Data;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.Serializable;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class SessionParam implements Serializable {

    private long uid;

    @Autowired
    EntityManager entityManager;


    public void setUserFilter() {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("userIdFilter");
        filter.setParameter("uid", uid);
    }
}