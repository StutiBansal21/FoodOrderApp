package com.upgrad.FoodOrderingApp.service.dao;


import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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
    {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactNumber", contactNumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity createAuthToken(CustomerAuthEntity customerAuthEntity)
    {
        this.entityManager.persist(customerAuthEntity);
        return  customerAuthEntity;
    }


    public void updateCustomer(CustomerEntity updatedCustomerEntity) {
        this.entityManager.merge(updatedCustomerEntity);
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

    public CustomerAuthEntity getAuthTokenByAccessToken(final String accessToken) {
        try {
            return entityManager.createNamedQuery("customerAuthByAccesstoken", CustomerAuthEntity.class).setParameter("accessToken", accessToken).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
