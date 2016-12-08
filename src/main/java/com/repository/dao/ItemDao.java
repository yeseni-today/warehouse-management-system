package com.repository.dao;

import com.repository.entity.ItemEntity;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Component
@Repository
public class ItemDao extends AbstractDao<ItemEntity> {
    public List<ItemEntity> queryByCategoryId(String categoryId) {
        if (categoryId==null || categoryId.trim().equals(""))
            return new ArrayList<>();
        String hql = "select e from ItemEntity e where e.categoryEntity.categoryId like '%"+categoryId+"%'";
        Session session = sessionFactory.openSession();
        List<ItemEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }

    public List<ItemEntity> getOne() {
        Session session = sessionFactory.openSession();
        String hql = "select e from ItemEntity e where ( e.itemCode='0000000000003' or e.itemCount>10 ) and e.itemPrice>10 ";
        List<ItemEntity> result = session.createQuery(hql).list();
        session.close();
        return result;
    }
}
