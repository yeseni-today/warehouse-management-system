package com.repository.dao;

import com.repository.entity.SdictionaryEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Finderlo on 11/16/2016.
 */
@Component
@Repository
public class SdictionaryDao extends AbstractDao<SdictionaryEntity> {
    public SdictionaryEntity getStorageEntity() {
        return findById("storage_ID");
    }
}
