package com.timecapsule.infra.entity;

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
    private String nickName;
    private String password;

    private Member(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public static Member of(String nickName, String password) {
        return new Member(nickName, password);
    }

}
