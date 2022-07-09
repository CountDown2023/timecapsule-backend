package com.timecapsule.infra.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "sample")
@Entity
public class SampleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private SampleEntity(String name) {
        this.name = name;
    }

    public static SampleEntity of(String name) {
        return new SampleEntity(name);
    }
}
