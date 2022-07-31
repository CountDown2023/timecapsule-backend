package com.timecapsule.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.timecapsule.infra.entity.Capsule;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CapsuleRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CapsuleRepository capsuleRepository;

    @Test
    public void list_convert_정상() {
        // given
        var goals = List.of(
            "일 열심히 하기", "사이드 프로젝트 열심히 하기"
        );
        var entity = Capsule.of(
            1L,
            "TEST",
            0, 0, 0, 0,
            goals
        );

        // when
        var saved = capsuleRepository.save(entity);

        // then
        assertThat(saved.getId()).isNotNull();
        var found = capsuleRepository.findById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getId()).isEqualTo(saved.getId());

        assertThat(found.get().getName()).isEqualTo(saved.getName());
        assertThat(found.get().getBottleChoice()).isEqualTo(saved.getBottleChoice());
        assertThat(found.get().getBottleColor()).isEqualTo(saved.getBottleColor());
        assertThat(found.get().getLetterPaper()).isEqualTo(saved.getLetterPaper());
        assertThat(found.get().getLetterLine()).isEqualTo(saved.getLetterLine());

        assertThat(found.get().getGoals()).isEqualTo(goals);
    }
}
