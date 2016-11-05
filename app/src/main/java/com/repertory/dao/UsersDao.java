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

        List<UsersEntity> usersEntities = usersDao.query
                (new String[]{"usersName","usersPassword"},new String[]{"xiao%","password%"},true);
//        List<UsersEntity> usersEntities = usersDao.query
//                (new String[]{"usersName"}, new String[]{"xiao%"}, true);
//        List<UsersEntity> usersEntities = usersDao.queryLike();
        usersEntities.forEach(e -> System.out.println(e.getUsersName() + e.getUsersPassword()));
//        usersDao.setUp();
//        usersDao.save(users);
//        Users users = usersDao.query("45646");
//        System.out.println(users);
//        users.setUsers_sex("女");
//        usersDao.update(users);

//        for (int i = 0; i < 10; i++) {
//            UsersEntity users = new UsersEntity();
//            users.setUsersId("123"+i);
//            users.setUsersIdentity("student"+i);
//            users.setUsersName("xiaoming"+i);
//            users.setUsersPassword("psd"+i);
//            users.setUsersPhone("123456"+i);
//            users.setUsersSex("男");
//            usersDao.save(users);
//        }


    }


}
