package integration.web;

import config.TestProperties;
import org.junit.jupiter.api.BeforeEach;
import org.nefedov.weather.application.dto.UserCreateDto;
import org.nefedov.weather.config.app.AppConfig;
import org.nefedov.weather.config.web.WebConfig;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {AppConfig.class, WebConfig.class, TestProperties.class})
@Sql(scripts = "classpath:clear_data.sql", config = @SqlConfig(separator = ";"))
public class WebTest {

    protected static final String LOGIN_FIELD = "login";
    protected static final String PASSWORD_FIELD = "password";
    protected static final String REPEATED_PASSWORD_FIELD = "repeatedPassword";
    protected static final UserCreateDto testUser = new UserCreateDto(
            "testlogin",
            "testpassword",
            "testpassword"
    );

    protected MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
