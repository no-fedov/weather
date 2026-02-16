package integration.web;

import org.junit.jupiter.api.BeforeEach;
import org.nefedov.weather.config.app.AppConfig;
import org.nefedov.weather.config.web.WebConfig;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(classes = {AppConfig.class, WebConfig.class})
@Sql(scripts = "classpath:clear_data.sql", config = @SqlConfig(separator = ";"))
public class WebTest {

    MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
