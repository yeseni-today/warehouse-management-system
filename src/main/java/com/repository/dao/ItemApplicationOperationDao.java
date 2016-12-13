package com.repository.dao;

import com.repository.common.Constants;
import com.repository.entity.ItemApplicationOperationEntity;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;


@Component
@Repository
public class ItemApplicationOperationDao extends AbstractDao<ItemApplicationOperationEntity> {

    @Transactional
    public List<ItemApplicationOperationEntity> findByDayBefore(int day) {
        Session session = sessionFactory.getCurrentSession();
        Date flag = new Date(System.currentTimeMillis() - Constants.DAY_1_MILLIS * day);
        return session
                .createQuery("from ItemApplicationOperationEntity e where e.applicationTime > :date")
                .setParameter("date", flag)
                .list();
    }
}
