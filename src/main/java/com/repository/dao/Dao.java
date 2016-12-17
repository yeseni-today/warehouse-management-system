package com.repository.dao;

import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by finderlo on 16-12-17.
 */
public interface Dao<T> extends ReadDao<T>  {

    public T findById(String parm);
    public T findByIds(Map<String, String> idAndValues);
    public void save(T t) ;

    public void saveOrUpdate(T t) ;

    public void delete(T t) ;

    public void update(T t) ;
}
