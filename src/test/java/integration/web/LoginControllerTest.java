package integration.web;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.nefedov.weather.application.exception.auth.SessionExpiredException;
import org.nefedov.weather.application.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DurationFormat;

import java.time.Duration;
import java.util.Objects;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;

public class LoginControllerTest extends WebTest {

    @Autowired
    private AuthService authService;

    @DurationFormat(defaultUnit = DurationFormat.Unit.MINUTES, style = DurationFormat.Style.SIMPLE)
    @Value("${app.session.expiration}")
    private Duration sessionExpiration;

    @Test
    public void userLogin_Successful() throws Exception {
        authService.registration(testUser);

        mockMvc.perform(post("/sign-in")
                        .formField(LOGIN_FIELD, testUser.getLogin())
                        .formField(PASSWORD_FIELD, testUser.getPassword()))
                .andExpectAll(
                        cookie().exists("session-id"),
                        cookie().value("session-id", not(emptyString()))
                );
    }

    @Test
    public void sessionExpiration() throws Exception {
        String sessionId = Objects.requireNonNull(mockMvc.perform(post("/sign-up")
                                .formField(LOGIN_FIELD, testUser.getLogin())
                                .formField(PASSWORD_FIELD, testUser.getPassword())
                                .formField(REPEATED_PASSWORD_FIELD, testUser.getRepeatedPassword()))
                        .andReturn()
                        .getResponse()
                        .getCookie("session-id"))
                .getValue();

        await().await()
                .pollDelay(sessionExpiration)
                .atMost(sessionExpiration.plus(Duration.ofSeconds(1)))
                .until(() -> true);

        assertEquals(SessionExpiredException.class, Objects.requireNonNull(mockMvc.perform(get("/home")
                        .cookie(new Cookie("session-id", sessionId)))
                .andReturn()
                .getResolvedException()).getClass());
    }
}
