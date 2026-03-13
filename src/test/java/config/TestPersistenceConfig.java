package config;

import org.nefedov.weather.config.app.DataSourceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import({DataSourceConfig.class})
@ComponentScan(basePackages = "org.nefedov.weather.application.persistence")
public class TestPersistenceConfig {
}
