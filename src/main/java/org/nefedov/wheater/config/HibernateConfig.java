package org.nefedov.wheater.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.nefedov.wheater.persistence.entity.Location;
import org.nefedov.wheater.persistence.entity.Session;
import org.nefedov.wheater.persistence.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class HibernateConfig {

    @Bean
    @DependsOn(value = "liquibase")
    public SessionFactory sessionFactory(DataSource dataSource) {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.datasource", dataSource)
                .applySetting("hibernate.show_sql", "true")
                .applySetting("hibernate.format_sql", "true")
                .applySetting("hibernate.hbm2ddl.auto", "validate")
                .build();
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Location.class);
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
