package com.rosterreview.config;

import javax.servlet.ServletContext;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This class registers a {@link DispatcherServlet} and loads the {@link AppConfig} class.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Specifies {@link org.springframework.context.annotation.Configuration} and/or
     * {@link org.springframework.stereotype.Component} classes for the root application context.
     * Currently registers {@link AppConfig} as the application configuration class.
     *
     * @return the configuration and component classes
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { AppConfig.class };
    }

    /**
     * Specifies {@link org.springframework.context.annotation.Configuration} and/or
     * {@link org.springframework.stereotype.Component} classes for the Servlet application context.
     *
     * @return the configuration and component classes
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {};
    }

    /**
     * Specifies the servlet mapping(s) for the {@link DispatcherServlet}.
     *
     * @return the servlet mappings
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * Registers a {@link DispatcherServlet} against the given {@link ServletContext}.
     * Also adds an instance of {@link JDBCDriverDeregistrationListener} to the servlet context.
     *
     * @param servletContext  the servlet context
     */
    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);

        servletContext.addListener(new JDBCDriverDeregistrationListener());
    }
}