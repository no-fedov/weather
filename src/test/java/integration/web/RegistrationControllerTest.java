package integration.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegistrationControllerTest extends WebTest {

    @Test
    public void registrationSignUp_Successful() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formField("login", "testlogin")
                        .formField("password", "testpassword")
                        .formField("repeatedPassword", "testpassword")
                )
                .andExpectAll(
                        status().is(HttpStatus.CREATED.value()),
                        cookie().exists("session-id"),
                        cookie().value("session-id", not(emptyString()))
                );
    }
}
