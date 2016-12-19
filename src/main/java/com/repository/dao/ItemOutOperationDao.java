package com.repository.dao;

import com.repository.entity.ItemOutOperationEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Repository
public class ItemOutOperationDao extends AbstractDao<ItemOutOperationEntity> {
    public List<ItemOutOperationEntity> findByState(String states){
        return findBy("outStates",states,false);
    }
}
