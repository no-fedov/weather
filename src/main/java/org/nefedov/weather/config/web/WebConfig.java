package org.nefedov.weather.config.web;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.jspecify.annotations.Nullable;
import org.nefedov.weather.config.app.AppConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Configuration
@Import({DispatcherConfig.class})
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

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        FilterRegistration.Dynamic authFilter = servletContext.addFilter(
                "authFilter",
                new DelegatingFilterProxy("authFilter")
        );
        authFilter.addMappingForUrlPatterns(null, true, "/*");
        FilterRegistration.Dynamic hiddenMethodFilter = servletContext.addFilter(
                "hiddenMethodFilter", new HiddenHttpMethodFilter()
        );
        hiddenMethodFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
