package com.repository.dao.view;

import com.repository.common.Constants;
import com.repository.dao.AbstractReadDao;
import com.repository.entity.view.ItemIndate;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by finderlo on 16-12-17.
 */
@Component
public class ItemInDateDao extends AbstractReadDao<ItemIndate>  {

    public List<ItemIndate> findByItemCode(String itemCode){
        return findBy("itemCode",itemCode,true);
    }

    public List<ItemIndate> findByDayBefore(int day) {
        Session session = sessionFactory.getCurrentSession();
        Date flag = new Date(System.currentTimeMillis() - Constants.DAY_1_MILLIS * day);
        List<ItemIndate> result = session
                .createQuery("from "+bindClassName()+" e where e.itemIndate > :date")
                .setParameter("date", flag)
                .list();
        System.out.println(result);
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
