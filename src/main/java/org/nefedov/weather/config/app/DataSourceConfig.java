package org.nefedov.weather.config.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        HikariConfig hikariConfig = new HikariConfig("hikari.properties");
        return new HikariDataSource(hikariConfig);
    }
}
