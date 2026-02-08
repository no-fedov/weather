package integration.db.persistence;

import org.junit.jupiter.api.extension.ExtendWith;
import org.nefedov.weather.config.AppConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
@Sql(value = "/clear_data_script.sql",
        config = @SqlConfig(separator = ";"),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class RepositoryTest {
}
