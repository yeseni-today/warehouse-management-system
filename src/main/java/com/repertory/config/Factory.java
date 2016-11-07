package com.repertory.config;


import com.repertory.dao.AbstractDao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.osgi.service.component.annotations.Component;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Finderlo on 2016/11/4.
 */
@Configuration
@ComponentScan
//@Component(service = AbstractDao.class)
public class Factory {

    @Bean(name = "sessionFactory")
    public static SessionFactory sessionFactory() {
        SessionFactory sessionFactory = setUp();
        if (sessionFactory == null) {
            for (int i = 0; i < 3; i++) {
                if ((sessionFactory = setUp()) != null) {
                    return sessionFactory;
                }
            }
        }
        return sessionFactory;
    }

    protected static SessionFactory setUp() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println("err");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            return null;
        }
        return sessionFactory;
    }

//    protected Configuration configuration() {
//        return
//                new Configuration()
//                        .setProperty("connection.driver_class", "com.mysql.jdbc.Driver")
//                        .setProperty("connection.url", "jdbc:mysql://127.0.0.1:3306/wms")
//                        .setProperty("connection.username", "root")
//                        .setProperty("connection.password", "123456")
//                        .setProperty("connection.pool_size", "5")
//                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
//
//
////                        .addAnnotatedClass(ItemApplicationEntity.class)
////                        .addAnnotatedClass(ItemApplicationOperationEntity.class)
////                        .addAnnotatedClass(ItemBorrowEntity.class)
////                        .addAnnotatedClass(ItemBorrowOperationEntity.class)
////                        .addAnnotatedClass(ItemCategoryEntity.class)
////                        .addAnnotatedClass(ItemCompanyEntity.class)
////                        .addAnnotatedClass(ItemEntity.class)
////                        .addAnnotatedClass(ItemInOperationEntity.class)
////                        .addAnnotatedClass(ItemInStorageEntity.class)
////                        .addAnnotatedClass(ItemOutStorageEntity.class)
////                        .addAnnotatedClass(ItemOutOperationEntity.class)
////                        .addAnnotatedClass(UmessageEntity.class)
////                        .addAnnotatedClass(UsersEntity.class)
//
//                        .setProperty("annotatedClass",ItemApplicationEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemApplicationOperationEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemBorrowEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemBorrowOperationEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemCategoryEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemCompanyEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemInOperationEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemInStorageEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemOutStorageEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",ItemOutOperationEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",UmessageEntity.class.getCanonicalName())
//                        .setProperty("annotatedClass",UsersEntity.class.getCanonicalName())
//
//                ;
//    }
}
