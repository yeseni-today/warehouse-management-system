package com.repository.dao;

import com.repository.common.Constants;
import com.repository.entity.ItemInOperationEntity;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

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

    @Transactional
    public List<ItemInOperationEntity> findByDayBefore(int day) {
        Session session = sessionFactory.getCurrentSession();
        Date flag = new Date(System.currentTimeMillis() - Constants.DAY_1_MILLIS * day);
        return session
                .createQuery("from ItemInOperationEntity e where e.storageTime > :date")
                .setParameter("date", flag)
                .list();
    }
}
