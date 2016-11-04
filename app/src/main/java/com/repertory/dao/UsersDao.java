//package com.repertory.dao;
//
//import com.repertory.Factory;
//import com.repertory.model.Users;
//
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.query.Query;
//
//import java.util.List;
//
//import javax.persistence.Parameter;
//
///**
// * Created by Finderlo on 2016/11/4.
// */
//public class UsersDao extends AbstractDao<Users> {
//
//
//    public static void main(String[] args) throws Exception {
////        Users users = new Users();
////        users.setUsers_ID("45646");
////        users.setUsers_identity("student");
////        users.setUsers_name("xiaoming");
////        users.setUsers_password(new char[]{'s', '5', 's', 'd'});
////        users.setUsers_phone("1826458");
////        users.setUsers_sex("男");
//        UsersDao usersDao = new UsersDao();
////        usersDao.setUp();
////        usersDao.save(users);
//        Users users = usersDao.query("45646");
//        System.out.println(users);
////        users.setUsers_sex("女");
////        usersDao.update(users);
//
//    }
//
//
//    String bindSql(String parm) {
//        return " from Users users where users.users_ID=" + parm;
//
//    }
//}
