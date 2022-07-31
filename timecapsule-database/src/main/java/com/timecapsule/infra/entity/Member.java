package com.timecapsule.infra.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String nickName;
    private String password;
    private String email;

    private Member(String nickName, String password, String email) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public static Member of(String nickName, String password, String email) {
        return new Member(nickName, password, email);
    }

}
