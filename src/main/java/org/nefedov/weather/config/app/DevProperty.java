package org.nefedov.weather.config.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("dev")
@Configuration
@PropertySource("classpath:application.properties")
public class DevProperty {
}
