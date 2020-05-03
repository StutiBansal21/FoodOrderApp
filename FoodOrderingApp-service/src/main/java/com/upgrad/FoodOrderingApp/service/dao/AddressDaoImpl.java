package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class AddressDaoImpl implements AddressDao{

    @PersistenceUnit//set of entities such that for any persistent identity there is a unique entity instance.
    private EntityManagerFactory entityManagerFactory;

    @Override
    public AddressEntity saveAddress(AddressEntity addressEntity) {
       EntityManager entityManager=entityManagerFactory.createEntityManager();
        EntityTransaction transaction=entityManager.getTransaction();
        try
        {
            transaction.begin();
            entityManager.persist(addressEntity);
            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            return null;
        }
        AddressEntity addressEntity1=searchByUuid(addressEntity.getUuid());
        addressEntity.setId(addressEntity1.getId());
        entityManager.close();
        return addressEntity;
    }

    //@Override
    public StateEntity getStateIdByUuid(String stateUuid) {
       /* EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<StateEntity> query = entityManager.
                createQuery("select u from StateEntity u where u.uuid = :uuid", StateEntity.class);
        List<StateEntity> list = query.setParameter("uuid", stateUuid).getResultList();
        entityManager.close();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);*/
        EntityManager entityManager = entityManagerFactory.createEntityManager();
         //the query is written which searches for the required in the database
        TypedQuery <StateEntity> query = entityManager.createQuery("select s from StateEntity s where s.uuid = :uuid", StateEntity.class);
        List<StateEntity> list = query.setParameter("uuid", stateUuid).getResultList();
        entityManager.close();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    @Override//this method retruns all the address oof the particular accessToken's customer from the table
    public List<AddressEntity> getAllAddresses(CustomerEntity customerEntity) {
        /*EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery <CustomerAddressEntity> query = entityManager.createQuery("select c from CustomerAddressEntity c where c.customerId = :customerId", CustomerAddressEntity.class);
        query.setParameter("customerId", customerEntity.getId());
        List <CustomerAddressEntity> customerAddressEntityList = query.getResultList();
        List <AddressEntity> list = new ArrayList<>();
        for(CustomerAddressEntity customerAddressEntity : customerAddressEntityList) {
            TypedQuery<AddressEntity> query2 = entityManager.createQuery("select u from AddressEntity u where u.id = :id", AddressEntity.class);
            query2.setParameter("id", customerAddressEntity.getAddressId());
            AddressEntity addressEntity = query2.getSingleResult();
            list.add(addressEntity);
        }
        entityManager.close();
        return list;*/
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //the query is written which searches for the required in the database
        TypedQuery <CustomerAddressEntity> query = entityManager
                .createQuery("select c from CustomerAddressEntity c where c.customerId = :customerId", CustomerAddressEntity.class);
        query.setParameter("customerId", customerEntity.getId());
        List <CustomerAddressEntity> customerAddressEntityList = query.getResultList();
        List <AddressEntity> list = new ArrayList<>();
        for(CustomerAddressEntity customerAddressEntity : customerAddressEntityList) {
            TypedQuery<AddressEntity> query2 = entityManager
                    .createQuery("select a From AddressEntity a where a.id = :id", AddressEntity.class);
            query2.setParameter("id", customerAddressEntity.getAddressId());
            AddressEntity addressEntity = query2.getSingleResult();
            list.add(addressEntity);
        }
        entityManager.close();
        return list;
    }

    @Override
    public String getStateNameByStateId(long stateId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //the query is written which searches for the required in the database
        TypedQuery <StateEntity> query = entityManager.
                createQuery("select u from StateEntity u where u.id = :id", StateEntity.class);
        List<StateEntity> list = query.setParameter("id", stateId).getResultList();

        entityManager.close();

        if(list.size() == 0)
            return null;
        else
            return list.get(0).getStateName();
    }

    @Override
    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            AddressEntity searched = entityManager.find(AddressEntity.class, addressEntity.getId());
            entityManager.remove(searched);
            transaction.commit();
        }
        catch (Exception e)
        {
            transaction.rollback();
            return null;
        }
        entityManager.close();
        return addressEntity;
    }

    @Override
    public AddressEntity searchByUuid(String addressUuid) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        //the query is written which searches for the required in the database
        TypedQuery <AddressEntity> query = entityManager.createQuery("select u from AddressEntity u where u.uuid = :uuid", AddressEntity.class);
        List<AddressEntity> list = query.setParameter("uuid", addressUuid).getResultList();
        entityManager.close();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    @Override
    public AddressEntity getAddressById(Long addressId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List <AddressEntity> list = new ArrayList<>();

        try {
            //the query is written which searches for the required in the database
            TypedQuery<AddressEntity> query = entityManager.createQuery("Select u from AddressEntity u where u.id = :addressId", AddressEntity.class);
            query.setParameter("addressId", addressId);
            list = query.getResultList();

        } catch (Exception e) {
            return null;
        }

        entityManager.close();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }
}
