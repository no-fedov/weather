package config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.http.HttpClient;

@Configuration
@PropertySource("classpath:application-test.properties")
public class TestProperties {

    @Bean
    public HttpClient httpClient() {
        return Mockito.mock(HttpClient.class);
    }
}
