package com.rosterreview.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This {@link Configuration} class creates Hibernate connections
 * to a {@link DataSource}.
 * <p>
 * The following configuration properties must be defined in
 * <code>classpath:datasource.properties</code>
 * <p>
 * <b>DataSource Properties</b>
 * <ul>
 * <li>ds.driver:   DataSource specific driver.</li>
 * <li>ds.url:      The location of the DataSource.</li>
 * <li>ds.username: DataSource access credential.</li>
 * <li>ds.password: DataSource access credential.</li>
 * </ul>
 * <p>
 * <b>Hibernate Properties</b>
 * <ul>
 * <li>hibernate.dialect:      Set the SQL dialect for the DataSource.</li>
 * <li>hibernate.hbm2ddl.auto: Set automatic schema generation.</li>
 * <li>hibernate.show_sql:     Enable logging of generated SQL statements.</li>
 * </ul>
 */

@Configuration
@EnableTransactionManagement
@PropertySource(value = { "classpath:datasource.properties" })
public class DataSourceConfig {

    @Autowired
    private Environment env;

    /**
     * Configures a {@link HibernateTemplate}.
     *
     * @return  a configured HibernateTemplate.
     */
    @Bean
    public HibernateTemplate getHibernateTemplate() {
        return new HibernateTemplate(getSessionFactory());
    }

    /**
     * Configures a {@link HibernateTransactionManager}.
     *
     * @return  a configured HibernateTransactionManager.
     */
    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(getSessionFactory());

        return txManager;
    }

    /**
     * Configures a {@link DataSource}.
     * <p>
     * DataSource configuration parameters are extracted from the application
     * environment. Specify these values in a properties file.
     *
     * @return  a configured DataSource
     */
    @Bean
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("ds.driver"));
        dataSource.setUrl(env.getProperty("ds.url"));
        dataSource.setUsername(env.getProperty("ds.username"));
        dataSource.setPassword(env.getProperty("ds.password"));

        return dataSource;
    }

    /**
     * Configures a {@link SessionFactory}
     * <p>
     * Entity classes must be stored in <code>com.rosterreview.entity</code>.
     *
     * @return  a configured SessionFactory
     */
    @Bean
    SessionFactory getSessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("com.rosterreview.entity")
                .addProperties(getHibernateProperties())
                .buildSessionFactory();
    }

    /**
     * Loads hibernate configuration {@link Properties} defined in
     * <code>classpath:datasource.properties</code>.
     *
     * @return  a configured Properties object
     */
    private Properties getHibernateProperties() {
        final String DIALECT = "hibernate.dialect";
        final String HBM2DDL = "hibernate.hbm2ddl.auto";
        final String SHOW_SQL = "hibernate.show_sql";

        Properties properties = new Properties();
        properties.put(DIALECT, env.getProperty(DIALECT));
        properties.put(HBM2DDL, env.getProperty(HBM2DDL));
        properties.put(SHOW_SQL, env.getProperty(SHOW_SQL));

        return properties;
    }
}
