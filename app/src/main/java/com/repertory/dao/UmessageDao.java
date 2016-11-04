package com.repertory.dao;

import com.repertory.Factory;
import com.repertory.bean.UmessageEntity;
import com.repertory.model.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.source.spi.EmbeddableMapping;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class UmessageDao {

//    Factory factory = new Factory();

    SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        UmessageEntity umessageEntity = new UmessageEntity();
        umessageEntity.setMessageId("id6565");
        umessageEntity.setMessageType("type44");
        umessageEntity.setMessageContent("444444");
        umessageEntity.setMessageDate(new Timestamp(System.currentTimeMillis()));
        umessageEntity.setMessageReceiveId("reerer");
        umessageEntity.setMessageSendId("sendid");

        UmessageDao umessageDao = new UmessageDao();
        umessageDao.setUp();
        umessageDao.save(umessageEntity);
    }

    protected  void setUp() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            System.out.println("err");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void save(UmessageEntity umessageEntity) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(umessageEntity);
        session.getTransaction().commit();
        session.close();
    }
}
