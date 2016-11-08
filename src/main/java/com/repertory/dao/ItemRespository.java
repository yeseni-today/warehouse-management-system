//package com.repertory.dao;
//
//import com.repertory.bean.ItemEntity;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Table;
//
///**
// * Created by Finderlo on 2016/11/7.
// */
//@Repository
//@Table(name = "item")
//public interface ItemRespository extends JpaRepository<ItemEntity,String>{
//
//
////    @PersistenceContext
////    private EntityManager entityManager;
////
////    public List<ItemEntity> findAll() {
////        return entityManager.createQuery("SELECT m FROM ItemEntity m", ItemEntity.class).getResultList();
////    }
//
//
//    public List<ItemEntity> findAll();
//}
