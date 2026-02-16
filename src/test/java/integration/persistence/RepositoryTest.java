package integration.persistence;

import org.junit.jupiter.api.extension.ExtendWith;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
public abstract class RepositoryTest {
}
