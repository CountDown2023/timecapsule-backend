package com.timecapsule.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.timecapsule.infra.entity.SampleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class SampleEntityRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @Test
    public void findById() {
        // given
        var entity = SampleEntity.of("뀨뀨찬찬");

        // when
        var saved = sampleEntityRepository.save(entity);

        // then
        assertThat(saved.getId()).isNotNull();
        var found = sampleEntityRepository.findById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }
}
