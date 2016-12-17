package com.repository.dao;

import com.repository.entity.UsersEntity;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
@EnableAsync
@EnableCaching
public class UsersDao extends AbstractDao<UsersEntity> {


    public static void main(String[] args) {
        UsersDao usersDao = new UsersDao();
        UsersEntity usersEntity  = new UsersEntity();
        usersEntity.setUsersId("4564");
        usersEntity.setUsersPassword("password5");
        System.out.println(""+usersDao.checkLogin(usersEntity));
    }

    public boolean checkLogin(UsersEntity usersEntity){
        UsersEntity usersEntity1 =  findById(usersEntity.getUsersId());
        if (usersEntity1 != null && usersEntity1.getUsersPassword().trim().equals(usersEntity.getUsersPassword().trim())){
            return true;
        }
        return false;
    }
}
