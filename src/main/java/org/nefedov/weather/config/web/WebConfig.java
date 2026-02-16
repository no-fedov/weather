package org.nefedov.weather.config.web;

import org.jspecify.annotations.Nullable;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@Import(DispatcherConfig.class)
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class<?>[]{AppConfig.class};
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class<?>[]{DispatcherConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
