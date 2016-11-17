package com.repository.dao;

import com.repository.entity.ItemCategoryEntity;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemCategoryDao extends AbstractDao<ItemCategoryEntity> {

    @Override
    public List<ItemCategoryEntity> findAll() {
        List<ItemCategoryEntity> result = super.findAll();
        return result.stream().filter(entity -> !checkIsNull(entity)).collect(Collectors.toList());
    }

    public boolean checkIsNull(ItemCategoryEntity entity) {
        if (entity.getCategoryName().trim().equals("A") || entity.getCategoryName().trim().equals("P")) {
            return true;
        } else return false;
    }
}
