package com.repository.dao;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.Map;

/**
 * Created by finderlo on 16-12-17.
 */
@EnableAsync
@EnableCaching
public interface Dao<T>   {


    public List<T> findAll();

    public List<T> findBy(String[] keys, String[] values);

    public List<T> findBy(String key, String value);

    public List<T> findBy(String key, String value, boolean isLikeQuery);

    public List<T> findBy(String[] keys, String[] values, boolean isLikeQuery);

    public T findById(String parm);
    public T findByIds(Map<String, String> idAndValues);
    public void save(T t) ;

    public void saveOrUpdate(T t) ;

    public void delete(T t) ;

    public void update(T t) ;
}
