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

    private static final String LOGIN_FIELD = "login";
    private static final String PASSWORD_FIELD = "password";
    public static final String REPEATED_PASSWORD_FIELD = "repeatedPassword";

    @Test
    public void registrationSignUp_Successful() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formField(LOGIN_FIELD, "testlogin")
                        .formField(PASSWORD_FIELD, "testpassword")
                        .formField(REPEATED_PASSWORD_FIELD, "testpassword")
                )
                .andExpectAll(
                        status().is(HttpStatus.CREATED.value()),
                        cookie().exists("session-id"),
                        cookie().value("session-id", not(emptyString()))
                );
    }

    @Test
    public void registrationWithUnuniqueLogin_Unsuccessful() throws Exception {
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formField(LOGIN_FIELD, "testlogin")
                .formField(PASSWORD_FIELD, "testpassword")
                .formField(REPEATED_PASSWORD_FIELD, "testpassword")
        );

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .formField(LOGIN_FIELD, "testlogin")
                        .formField(PASSWORD_FIELD, "testpassword")
                        .formField(REPEATED_PASSWORD_FIELD, "testpassword")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}
