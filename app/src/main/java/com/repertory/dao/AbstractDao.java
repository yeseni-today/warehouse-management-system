package com.repertory.dao;

import com.repertory.Factory;
import com.repertory.bean.UmessageEntity;
import com.repertory.model.Users;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Created by Finderlo on 2016/11/4.
 */
public abstract class AbstractDao<T> {
    SessionFactory sessionFactory = Factory.getSessionFactory();

    public void save(T t) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    public T query(String parm) {
        Session session = sessionFactory.openSession();
        String hql = bindSql(parm);
        Query query = session.createQuery(hql);
        List<T> tList = query.list();
        session.close();
        if (tList.isEmpty()) {
            return null;
        }
        return tList.get(0);
    }

    abstract String bindSql(String sql);

    public void update(T t) {
        if (t == null) {
            return;
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }
}
