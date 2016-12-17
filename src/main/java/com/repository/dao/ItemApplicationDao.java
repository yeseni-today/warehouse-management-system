package com.repository.dao;

import com.repository.entity.ItemApplicationEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class ItemApplicationDao extends AbstractDao<ItemApplicationEntity> {

    public List<ItemApplicationEntity> findByApplyId(String apply_id) {
        return findBy("applicationId",apply_id,false);
    }
}
