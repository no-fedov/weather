package org.nefedov.weather;

import org.nefedov.weather.application.service.WeatherClient;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Runner {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        WeatherClient bean = context.getBean(WeatherClient.class);

        System.out.println();


    }
}
