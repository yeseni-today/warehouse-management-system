package com.repository.dao;

import com.repository.entity.ItemInOperationEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Finderlo on 2016/11/5.
 */
@Component
@Repository
public class ItemInOperationDao extends AbstractDao<ItemInOperationEntity> {

    @Override
    public void save(ItemInOperationEntity entity) {
        super.save(entity);
    }
}
