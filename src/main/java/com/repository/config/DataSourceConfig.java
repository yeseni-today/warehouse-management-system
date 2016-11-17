//package com.repository.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
///**
// * Created by Finderlo on 2016/11/8.
// */
//@Configuration
//public class DataSourceConfig {
//
//
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driverClassName}")
//    private String dirver;
//    @Value("${spring.jpa.properties.hibernate.dialect}")
//    private String dialog;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(dirver);
//        return dataSource;
//    }
//
////    @Bean
////    public LocalSessionFactoryBean sessionFactory() {
////        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
////        sessionFactoryBean.setDataSource(dataSource());
////        sessionFactoryBean.setPackagesToScan(new String[]{"com.repository.entity"});
////        Properties properties = new Properties();
////        properties.setProperty("dialect", dialog);
////        sessionFactoryBean.setHibernateProperties(properties);
////        System.out.println("sessionFactory");
////        return sessionFactoryBean;
////    }
//
////    @Bean
////    @Primary
////    public SessionFactory sessionFactory(){
////        return localSessionFactoryBean().getObject();
////    }
////
////    @Bean
////    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory){
////        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
////
////        transactionManager.setSessionFactory(sessionFactory);
////        return transactionManager;
////    }
//}
