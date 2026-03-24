package org.nefedov.weather.config.app;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Controller;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@ComponentScan(
        basePackages = {"org.nefedov.weather.application", "org.nefedov.weather.config.app"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Controller.class
        )
)
public class AppConfig {

    @Profile("dev")
    @Configuration
    @PropertySource("classpath:application.properties")
    static class DevProperty {
    }

    @Bean
    public HttpClient httpClient(@Value("${weather.api.connect.timeout.sec}") long connectionTimeout) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    @Bean
    public JsonMapper jsonMapper() {
        JsonMapper mapper = new JsonMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Bean
    public FormattingConversionService conversionService() {
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(false);
        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.registerFormatters(conversionService);
        return conversionService;
    }

    @Bean
    public BCrypt.Hasher passwordHasher() {
        return BCrypt.withDefaults();
    }

    @Bean
    public BCrypt.Verifyer passwordVerifyer() {
        return BCrypt.verifyer();
    }
}
