package org.nefedov.weather.config.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = {"org.nefedov.weather.application", "org.nefedov.weather.config.app"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = Controller.class))
public class AppConfig {
}
