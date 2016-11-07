package com.repertory.dao;

import com.repertory.bean.ItemCompanyEntity;

import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class ItemCompanyDao extends AbstractDao<ItemCompanyEntity>{

    public static void main(String[] args) {
        ItemCompanyDao itemCompanyDao = new ItemCompanyDao();
        String sql = "from UsersEntity ";
        Session session =itemCompanyDao.sessionFactory.openSession();
        Query query = session.createQuery(sql).setParameter("usersId","5").setParameter("usersPassword","6");
        System.out.println(query.getQueryString());
    }
}
