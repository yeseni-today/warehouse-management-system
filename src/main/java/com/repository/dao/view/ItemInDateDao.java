package com.repository.dao.view;

import com.repository.common.Constants;
import com.repository.dao.AbstractReadDao;
import com.repository.dao.Dao;
import com.repository.dao.ReadDao;
import com.repository.entity.ItemApplicationOperationEntity;
import com.repository.entity.view.ItemIndate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by finderlo on 16-12-17.
 */
@Component
public class ItemInDateDao extends AbstractReadDao<ItemIndate> implements ReadDao<ItemIndate> {

    public List<ItemIndate> findByItemCode(String itemCode){
        return findBy("itemCode",itemCode,false);
    }

    public List<ItemIndate> findByDayBefore(int day) {
        Session session = sessionFactory.getCurrentSession();
        Date flag = new Date(System.currentTimeMillis() - Constants.DAY_1_MILLIS * day);
        List<ItemIndate> result = session
                .createQuery("from "+bindClassName()+" e where e.itemIndate > :date")
                .setParameter("date", flag)
                .list();

        return sort(result);
    }

    public List<ItemIndate> findByItemName(String itemName){
        return findBy("itemName",itemName,true);
    }

    @Override
    public List<ItemIndate> sort(List<ItemIndate> itemIndates){
        itemIndates.sort(Comparator.comparingLong(e -> e.getItemIndate().getTime()));
        Collections.reverse(itemIndates);
        return itemIndates;
    }


}
