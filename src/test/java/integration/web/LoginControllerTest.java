package integration.web;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.core.IsNot.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;

public class LoginControllerTest extends WebTest {


    @Test
    public void userLogin_Successful() throws Exception {
        mockMvc.perform(post("/sign-up")
                .formField("login", "testlogin")
                .formField("password", "testpassword")
                .formField("repetedPassword", "testpassword"));

        mockMvc.perform(post("/sign-in")
                        .formField("login", "testlogin")
                        .formField("password", "testpassword"))
                .andExpectAll(
                        cookie().exists("session-id"),
                        cookie().value("session-id", not(emptyString()))
                );
    }
}
