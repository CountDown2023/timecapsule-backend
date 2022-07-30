package com.timecapsule.infra.repository;

import com.timecapsule.infra.config.DatabaseConfig;
import javax.transaction.Transactional;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.config.location=classpath:application-database.yml")
@SpringBootTest(classes = DatabaseConfig.class)
@ExtendWith(SpringExtension.class)
public class BaseRepositoryTest {

}
