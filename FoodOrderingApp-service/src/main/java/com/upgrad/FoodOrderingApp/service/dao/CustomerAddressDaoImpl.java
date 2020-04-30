package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CustomerAddressDaoImpl {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerAddressEntity searchByAddressId(long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        TypedQuery<CustomerAddressEntity> query = entityManager.createQuery("select c from CustomerAddressEntity c where c.addressId = :addressId", CustomerAddressEntity.class);
        List<CustomerAddressEntity> list = query.setParameter("addressId", addressId).getResultList();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }


    public  CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            entityManager.persist(customerAddressEntity);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            return null;
        }
        return customerAddressEntity;
    }

    public CustomerAddressEntity deleteCustomerAddress(CustomerAddressEntity customerAddressEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {

            tx.begin();
            entityManager.remove(customerAddressEntity);
            tx.commit();
        }
        catch (Exception e)
        {
            tx.rollback();
            return null;
        }
        return customerAddressEntity;
    }
}
