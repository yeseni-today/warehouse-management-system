package com.repository.dao;

import com.repository.entity.ItemCompanyEntity;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ItemCompanyDao extends AbstractDao<ItemCompanyEntity>{

    public static void main(String[] args) {
        ItemCompanyDao itemCompanyDao = new ItemCompanyDao();
        String sql = "from UsersEntity ";
        Session session =itemCompanyDao.sessionFactory.openSession();
        Query query = session.createQuery(sql).setParameter("usersId","5").setParameter("usersPassword","6");
        System.out.println(query.getQueryString());
    }
}
