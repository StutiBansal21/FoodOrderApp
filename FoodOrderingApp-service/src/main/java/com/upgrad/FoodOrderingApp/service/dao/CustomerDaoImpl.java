package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDaoImpl implements CustomerDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return  customerEntity;
    }

    @Override
    public CustomerEntity getCustomer(String contactNumber) {
        return null;
    }

    @Override
    public boolean getCustomer(boolean b) {
        return false;
    }

    @Override
    public CustomerAuthEntity getCustomerAuthByAccesstoken(String access_token) {
        return null;
    }

    public  CustomerEntity getCustomerByCno(final String cno)
    {
     try
     {
      return entityManager.createNamedQuery("customerByContactNumber",CustomerEntity.class).setParameter("contact_number",cno).getSingleResult();
     }
     catch(NoResultException e)
     {
         return null;
     }
    }

      public CustomerAuthEntity createAuthToken(CustomerAuthEntity cat)
      {
        this.entityManager.persist(cat);
        return  cat;
      }

      public void updateCustomer(CustomerEntity uct)
      {
       this.entityManager.merge(uct);
      }
  /*  @Override
    public boolean getCustomer(boolean b) {
        return false;
    }*/
}
