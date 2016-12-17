package com.repository.dao;


import com.repository.base.BaseObject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Component
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractDao<T extends Object> extends AbstractReadDao<T> implements  Dao<T>{


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
