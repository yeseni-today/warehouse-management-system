package com.repository.dao;

import com.repository.entity.ItemEntity;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ItemDao extends AbstractDao<ItemEntity> {

    public List<ItemEntity> queryByCategoryId(String categoryId) {
        if (categoryId==null || categoryId.trim().equals(""))
            return new ArrayList<>();
        String hql = "select e from ItemEntity e where e.itemCategoryEntity.categoryId like '%"+categoryId+"%'";
        Session session = sessionFactory.openSession();
        Query<ItemEntity> queueT = session.createQuery(hql);
        List<ItemEntity> result = queueT.list();
        session.close();
        return result;
    }
}
