package com.repertory.dao;

import com.repertory.Factory;
import com.repertory.bean.UsersEntity;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.security.PublicKey;
import java.util.List;

import javax.persistence.Parameter;

/**
 * Created by Finderlo on 2016/11/4.
 */
public class UsersDao extends AbstractDao<UsersEntity> {


    public static void main(String[] args) {

        UsersDao usersDao = new UsersDao();

        UsersEntity usersEntity  = new UsersEntity();
        usersEntity.setUsersId("4564");
        usersEntity.setUsersPassword("password5");
        System.out.println(""+usersDao.checkLogin(usersEntity));
    }

    public boolean checkLogin(UsersEntity usersEntity){
        UsersEntity usersEntity1 =  queryById(usersEntity.getUsersId());
        if (usersEntity1 != null && usersEntity1.getUsersPassword().equals(usersEntity.getUsersPassword())){
            return true;
        }
        return false;
    }
}
