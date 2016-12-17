package com.repository.dao;


import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


//@Component
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractDao<T> extends AbstractReadDao<T> {


    public void save(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.save(t);
    }

    public void saveOrUpdate(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(t);
    }

    public void delete(T t) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(t);
    }

    public void update(T t) {
        if (t == null) {
            return;
        }
        Session session = sessionFactory.getCurrentSession();
        session.update(t);
    }

}
