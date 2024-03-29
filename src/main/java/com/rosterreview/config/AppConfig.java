package com.rosterreview.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * This {@link Configuration} class declares multiple {@link Bean} methods,
 * and may be processed by the Spring container to generate bean definitions
 * and service requests for those beans at runtime. This class also defines
 * callback methods to customize the Java-based configuration for Spring MVC
 * enabled via {@link EnableWebMvc}.
 */

@Configuration
@ComponentScan("com.rosterreview")
@PropertySource(value = { "classpath:rosterreview.properties" })
@EnableScheduling
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configures an {@link ObjectMapper} bean.
     *
     * @return  an configured ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper mapper = ((MappingJackson2HttpMessageConverter) converter)
                        .getObjectMapper();
                mapper.registerModule(new Hibernate5Module());
            }
        }
    }

    /**
     * Registers a view controller for the base url.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/value-chart/").setViewName("forward:/index.html");
        registry.addViewController("/player-search").setViewName("forward:/index.html");
        registry.addViewController("/player/**").setViewName("forward:/index.html");
    }

    /**
     * Registers the location of static resources such as images, html, js,
     * and css files.
     *
     * @param registry  a resource handler registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String BUILD_DIR = "/WEB-INF/roster-review-app/build/";

        registry.addResourceHandler("/static/**")
            .addResourceLocations(BUILD_DIR.concat("static/"));
        registry.addResourceHandler("/index.html")
            .addResourceLocations(BUILD_DIR.concat("index.html"));
    }
}
