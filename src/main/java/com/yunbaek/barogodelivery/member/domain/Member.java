package com.yunbaek.barogodelivery.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    protected Member() {
    }

    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
