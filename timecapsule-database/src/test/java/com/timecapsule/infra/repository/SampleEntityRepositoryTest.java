package com.timecapsule.infra.repository;

import com.timecapsule.infra.config.DatabaseConfig;
import com.timecapsule.infra.entity.SampleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@TestPropertySource(properties = "spring.config.location=classpath:application-database.yml")
@SpringBootTest(classes = DatabaseConfig.class)
@ExtendWith(SpringExtension.class)
class SampleEntityRepositoryTest {
    @Autowired
    private SampleEntityRepository sampleEntityRepository;

    @BeforeEach
    public void init() {
        sampleEntityRepository.deleteAll();
    }

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