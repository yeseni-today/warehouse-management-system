//package com.repertory.dao;
//
//import com.repertory.bean.UsersEntity;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Table;
//
///**
// * Created by Finderlo on 2016/11/7.
// */
//@Repository
//@Table(name = "users")
//public interface UserRespository  extends JpaRepository<UsersEntity,String>{
//
////    @PersistenceContext
////    private EntityManager entityManager;
////
////    public UsersEntity findById(String id){
////       return entityManager.createQuery("select a from UsersEntity a where a.id = :id",UsersEntity.class)
////                .setParameter("id",id)
////                .getSingleResult();
////    }
//    public UsersEntity findById(String id);
//
//
//}
