package com.repository.dao;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

/**
 * Created by finderlo on 16-12-17.
 */
@EnableAsync
@EnableCaching
public interface ReadDao<T> {
    public T findById(String id);

    public List<T> findAll();

    public List<T> findBy(String[] keys, String[] values);

    public List<T> findBy(String key, String value);

    public List<T> findBy(String key, String value, boolean isLikeQuery);

    public List<T> findBy(String[] keys, String[] values, boolean isLikeQuery);
}
