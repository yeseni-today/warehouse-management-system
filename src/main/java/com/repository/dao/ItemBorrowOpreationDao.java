package com.repository.dao;

import com.repository.entity.ItemBorrowEntity;
import com.repository.entity.ItemBorrowOperationEntity;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemBorrowOpreationDao extends AbstractDao<ItemBorrowOperationEntity> {

    public List<ItemBorrowOperationEntity> findAll() {
        List<ItemBorrowOperationEntity> list = super.findAll();
        list.forEach(entity -> setOneToMany(entity));
        return list;
    }

    private void setOneToMany(ItemBorrowOperationEntity entity) {
        Session session = sessionFactory.openSession();
        String hql = "select e from ItemBorrowEntity e where e.borrowId=" + entity.getBorrowId();
        Query<ItemBorrowEntity> query = session.createQuery(hql);
        query.list().forEach(entity1 -> entity.getItemBorrowEntities().add(entity1));
    }
}
