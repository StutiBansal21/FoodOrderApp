package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CustomerAuthEntityDaoImpl implements CustomerAuthEntityDao{

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomerAuthEntity create(CustomerAuthEntity customerAuthEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(customerAuthEntity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            System.out.println(e);
            return null;
        }
        return customerAuthEntity;
    }

    @Override
    public CustomerAuthEntity updateCustomer(CustomerAuthEntity customerAuthEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            entityManager.merge(customerAuthEntity);
            tx.commit();
            System.out.println("customerAuthEntity updated with UUID : " + customerAuthEntity.getUuid());

        } catch (Exception e) {
            tx.rollback();
            System.out.println(e);
            return null;
        }
        return customerAuthEntity;
    }

    @Override
    public CustomerAuthEntity getAuthTokenByAccessToken(String accessToken) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
       TypedQuery<CustomerAuthEntity> query = entityManager.createQuery("select c from CustomerAuthEntity c where c.accessToken = :accessToken", CustomerAuthEntity.class);
        List<CustomerAuthEntity> list = query.setParameter("accessToken", accessToken).getResultList();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }/**
      try {
            return entityManager.createNamedQuery("customerAuthByAccesstoken", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
       } }*/

}
