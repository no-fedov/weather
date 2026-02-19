package org.nefedov.weather.config.app;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.stereotype.Controller;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
@ComponentScan(basePackages = {"org.nefedov.weather.application", "org.nefedov.weather.config.app"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = Controller.class))
public class AppConfig {

    @Bean
    public HttpClient httpClient() {
//            @Value("${connectTimeoutSec}") long connectionTimeout) {
        return HttpClient.newBuilder()
//                .connectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

    @Bean
    public JsonMapper jsonMapper() {
        JsonMapper mapper = new JsonMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
