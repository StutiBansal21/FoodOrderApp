package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class RestaurantDaoImpl implements RestaurantDao{

    @PersistenceUnit//defines a set of all entity classes that are managed by EntityManager instances in an application
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext//set of entities such that for any persistent identity there is a unique entity instance.
    private EntityManager entityManager;


    //this method returns the restaurant details by the rrestaurant name in the database
    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName)
    {
        //EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<RestaurantEntity>list=new ArrayList<>();
        String str = "%"+restaurantName.toLowerCase()+"%";
        try {
            //the query is written which searches for the required in the database
            TypedQuery<RestaurantEntity> query = entityManager.createQuery("select r from RestaurantEntity r WHERE LOWER(r.restaurantName) LIKE :str ORDER BY r.restaurantName", RestaurantEntity.class);
            query.setParameter("str", str);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();// closes an entity manager to release its persistence context and other resources
       /* if(list.size()==0)
            return null;
        else
            return list.get(0);*/
        return list;
    }

    //this method rerturns the restaurant details using the restaurantId in the database
    public RestaurantEntity getRestaurantById(final Long restaurantId){
        //EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<RestaurantEntity>list=new ArrayList<>();
        try {
            //the query is written which searches for the required in the database
            TypedQuery <RestaurantEntity> query = entityManager.createQuery("SELECT r FROM RestaurantEntity r WHERE r.id = :restaurantId ORDER BY r.restaurantName", RestaurantEntity.class);
            query.setParameter("restaurantId", restaurantId);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();// closes an entity manager to release its persistence context and other resources
        if (list.size()==0)
            return null;
        else
            return list.get(0);

    }

    //returns the restaurants by uuid in the database
    public RestaurantEntity getRestaurantByUuid(final String restaurantUuid){
        //EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<RestaurantEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database

            //This is always preferred if we know our Query result type beforehand. Additionally, it makes our code much more reliable and easier to test.
            TypedQuery <RestaurantEntity> query = entityManager.createQuery("SELECT r FROM RestaurantEntity r WHERE r.uuid = :restaurantUuid", RestaurantEntity.class);
            query.setParameter("restaurantUuid", restaurantUuid);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();// closes an entity manager to release its persistence context and other resources
        if (list.size()==0)
            return null;
        else
            return list.get(0);
    }

    //Query: retrieves the matching record from the users table and also maps it to the UserEntity object.
}
