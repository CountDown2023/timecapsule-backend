package com.timecapsule.infra.entity;

import com.timecapsule.infra.converter.StringListConverter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Capsule extends BaseTimeEntity {

    @Id
    private Long id;
    private Long userId;

    private String name;
    private Integer bottleChoice; // 0~7
    private Integer bottleColor;
    private Integer letterPaper;
    private Integer letterLine;

    // 우선 String
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> goals;
}
