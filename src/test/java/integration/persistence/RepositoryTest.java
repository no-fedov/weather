package integration.persistence;

import config.TestPersistenceConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestPersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
public abstract class RepositoryTest {

    protected static final String USER_LOGIN_TEST = "test_login";
    protected static final String USER_PASSWORD_TEST = "test_password";
}
