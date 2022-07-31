package com.timecapsule.infra.entity;

import com.timecapsule.infra.converter.StringListConverter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Capsule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long memberId;

    private String name;
    private Integer bottleChoice; // 0~7
    private Integer bottleColor;
    private Integer letterPaper;
    private Integer letterLine;

    // 우선 String
    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> goals;

    private Capsule(Long memberId, String name, Integer bottleChoice, Integer bottleColor, Integer letterPaper,
        Integer letterLine, List<String> goals) {
        this.memberId = memberId;
        this.name = name;
        this.bottleChoice = bottleChoice;
        this.bottleColor = bottleColor;
        this.letterPaper = letterPaper;
        this.letterLine = letterLine;
        this.goals = goals;
    }

    public static Capsule of(
        Long memberId,
        String name,
        Integer bottleChoice,
        Integer bottleColor,
        Integer letterPaper,
        Integer letterLine,
        List<String> goals
    ) {
        return new Capsule(
            memberId,
            name,
            bottleChoice,
            bottleColor,
            letterPaper,
            letterLine,
            goals
        );
    }
}
