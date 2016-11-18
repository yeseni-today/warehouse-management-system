package com.repository.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import java.util.Properties;

import javax.sql.DataSource;


@Configuration
@PropertySource(value = "classpath:/lx_jdbc.properties")
@Profile({"lx"})
public class SqlServerDataSourceConfig {

    @Value("${db.driver}")
    private String DB_DRIVER;

    @Value("${db.url}")
    private String DB_URL;

    @Value("${hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;
    @Value("${hibernate.default.schma}")
    private String HIBERNATE_SCHMA;
    @Value("${hibernate.default.catalog}")
    private String HIBERNATE_CATALOG;

    @Bean
    @Conditional(AssertSqlServer.class)
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        return dataSource;
    }

    @Bean
    @Conditional(AssertSqlServer.class)
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.default_schema", HIBERNATE_SCHMA);
        hibernateProperties.put("hibernate.default_catalog", HIBERNATE_CATALOG);
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

//    @Bean
//    public HibernateTransactionManager transactionManager() {
//        HibernateTransactionManager transactionManager =
//                new HibernateTransactionManager();
//        transactionManager.setSessionFactory(sessionFactory().getObject());
//        return transactionManager;
//    }

} // class DatabaseConfig
