package com.repository.dao;

import com.repository.entity.SdictionaryEntity;

import org.springframework.stereotype.Component;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Component
public class SdictionaryDao extends AbstractDao<SdictionaryEntity> {

    public static void main(String[] args) {
//        new SdictionaryDao().findAll().forEach(entity -> {
//            System.out.println(new StringBuilder().append(entity.getField()).append(":table:").append(entity.getTable()).toString());
//        });
        System.out.println(new SdictionaryDao().findById("storage_ID").getField());
    }
}
