package com.repository.dao;

import com.repository.entity.CategoryEntity;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Repository
public class CategoryDao extends AbstractDao<CategoryEntity> {

    @Override
    public List<CategoryEntity> findAll() {
        List<CategoryEntity> result = super.findAll();
        result.forEach(e -> e.setCategoryName(e.getCategoryName().trim()));
        return result.stream().filter(entity -> !checkIsNull(entity)).collect(Collectors.toList());
    }

    public boolean checkIsNull(CategoryEntity entity) {
        if (entity.getCategoryName().trim().equals("A") || entity.getCategoryName().trim().equals("P")) {
            return true;
        } else return false;
    }

    public boolean addCategory(boolean isA, String name) {
        List<CategoryEntity> result = super.findAll();
        result.forEach(e -> e.setCategoryName(e.getCategoryName().trim()));
        List<CategoryEntity> nullvalue = result
                .stream()
                .filter(entity -> checkIsNull(entity))
                .collect(Collectors.toList());
        for (CategoryEntity category : nullvalue) {
            if (category.getCategoryName().trim().equals("A")==isA) {
                //是A类
                    category.setCategoryName(name);
                    update(category);
                    return true;
            }else {
                continue;
            }
        }
        return false;
    }
}
