package com.rosterreview.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

/**
 * This class manually deregisters the JDBC driver upon application shutdown.  Without this
 * class, Tomcat will automatically do this to prevent a memory leak, but will leave an ugly
 * warning in the logs.  For more information, see:
 * <a href="https://stackoverflow.com/questions/3320400/to-prevent-a-memory-leak-the-jdbc-driver-has-been-forcibly-unregistered/23912257#23912257">stackoverflow.com</a>
 * and <a href="https://github.com/spring-projects/spring-boot/issues/2612">github.com/spring-projects</a>.
 */
public class JDBCDriverDeregistrationListener implements ServletContextListener {

    private Logger log = LoggerFactory.getLogger(JDBCDriverDeregistrationListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Initialising Context...");
    }

    @Override
    public final void contextDestroyed(ServletContextEvent sce) {

        log.info("Destroying Context...");

        log.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
        AbandonedConnectionCleanupThread.checkedShutdown();

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            if (driver.getClass().getClassLoader() == cl) {

                try {
                    log.info("Deregistering JDBC driver {}", driver);
                    DriverManager.deregisterDriver(driver);

                } catch (SQLException ex) {
                    log.error("Error deregistering JDBC driver {}", driver, ex);
                }

            } else {
                log.trace("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader",
                        driver);
            }
        }
    }
}