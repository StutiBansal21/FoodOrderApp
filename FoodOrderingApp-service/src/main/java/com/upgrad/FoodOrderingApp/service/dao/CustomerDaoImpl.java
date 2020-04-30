package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class CustomerDaoImpl implements CustomerDao{

    @PersistenceContext//set of entities such that for any persistent identity there is a unique entity instance.
    private EntityManager entityManager;

    @Override//the method in the interface is implemented here and overridden
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) //this method is for signup which will save users/customers int eh db
    {
        entityManager.persist(customerEntity);
        return  customerEntity;
    }

    public CustomerEntity getCustomerByContactNumber(final String contactNumber) //to check whether there already exists a customer in the db having the same contactnumber
    {/*
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", contactNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }*/

        //the query is written which searches for the required in the database
        TypedQuery <CustomerEntity> query = entityManager.createQuery("SELECT c from CustomerEntity c where c.contactNumber = :contactNumber",
                CustomerEntity.class);

        List <CustomerEntity> list = query.setParameter("contactNumber", contactNumber).getResultList();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    public CustomerEntity searchById(final long id) {

        TypedQuery <CustomerEntity> query = entityManager.createQuery("SELECT c from CustomerEntity c where c.id = :id",
                CustomerEntity.class);

        List <CustomerEntity> list = query.setParameter("id", id).getResultList();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);

    }

    public CustomerAuthEntity createAuthToken(CustomerAuthEntity customerAuthEntity)
    {
        this.entityManager.persist(customerAuthEntity);
        return  customerAuthEntity;
    }
    public  CustomerAuthEntity updateCustomer(final CustomerAuthEntity customerAuthEntity)
    {
        EntityTransaction entityTransaction=entityManager.getTransaction();
        try
        {
            entityTransaction.begin();
            entityManager.merge(customerAuthEntity);
            entityTransaction.commit();
            //System.out.println("customerAuthEntity updated with UUID: "+customerAuthEntity.getUuid());
        }
        catch (Exception e)
        {
            entityTransaction.rollback();
            return null;
        }
         return customerAuthEntity;
    }
    public void updateUser(final CustomerEntity customerEntity) {
        try {
            entityManager.merge(customerEntity);

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /*public CustomerAuthEntity getAuthTokenByAccessToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthByAccesstoken", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }*/
    public CustomerAuthEntity updateLoginInfo(final CustomerAuthEntity userAuthEntity) {
        try {
            entityManager.persist(userAuthEntity);
            return userAuthEntity;
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity getCustomerAuthEntityTokenByUUID(final String uuid) {
        /*try {
            return entityManager.createNamedQuery("userAuthTokenByUUID", CustomerAuthEntity.class).setParameter("uuid", UUID).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }*/
        TypedQuery <CustomerEntity> query = entityManager.createQuery("SELECT c from CustomerEntity c where c.uuid = :uuid",CustomerEntity.class);

        List <CustomerEntity> list = query.setParameter("uuid", uuid).getResultList();
        if(list.size() == 0)
            return null;
        try {
            return entityManager.createNamedQuery("userAuthTokenByUUID", CustomerAuthEntity.class).setParameter("uuid", uuid).getSingleResult();
        } catch (NoResultException nre) {
            return null;}

    }

    public CustomerAuthEntity updateUserLogOut(final CustomerAuthEntity customerAuthEntity) {
        try {
            return entityManager.merge(customerAuthEntity);
        } catch (NoResultException nre) {
            return null;
        }
    }
    public CustomerEntity searchByUuid(final String uuid) {

        TypedQuery <CustomerEntity> query = entityManager.createQuery("SELECT c from CustomerEntity c where c.uuid = :uuid",CustomerEntity.class);

        List <CustomerEntity> list = query.setParameter("uuid", uuid).getResultList();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }


}
