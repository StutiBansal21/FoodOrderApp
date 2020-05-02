package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDaoImpl implements RestaurantDao {

    @PersistenceContext //set of entities such that for any persistent identity there is a unique entity instance.
    private EntityManager entityManager;

    // function to get Restaurant using the id
    public RestaurantEntity getRestaurantUsingId(final long restaurantId)
    {
        // list of type RestaurantEntity
        List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
        try
        {
            // query which used to complete requirement using database
            TypedQuery<RestaurantEntity> query =entityManager.createQuery("SELECT r from RestaurantEntity WHERE r.id := restaurantId ORDER BY r.restaurantName",RestaurantEntity.class);
            query.setParameter("restaurantId",restaurantId);
            restaurantEntityList = query.getResultList();
        }
        catch (Exception ex)
        {
            return null;
        }
        entityManager.close();//closes an entity manager to release its persistence context and other resources
        if(restaurantEntityList.size()==0)
            return null;
        else
            return restaurantEntityList.get(0);
    }

    // function to get the restaurant using name
    public List<RestaurantEntity> getRestaurantUsingName(final String restaurantName)
    {
        // list of type restaurantEntity
        List<RestaurantEntity> restaurantEntityList= new ArrayList<>();
        String str= "%"+restaurantName.toLowerCase()+"%"; // it converts the restaurant name to lower case
        try
        {
        TypedQuery<RestaurantEntity> query = entityManager.createQuery("SELECT r FROM RestaurantEntity r WHERE LOWER(r.restaurantName) LIKE :str ORDER BY r.restaurantName",RestaurantEntity.class);
        query.setParameter("str",str);
        restaurantEntityList = query.getResultList();
        }
        catch (Exception e)
        {
            return null;
        }
        entityManager.close();
        return restaurantEntityList; //  return the list of restaurantEntity
    }

    // function to get the name of restaurant by using Uuid
        public RestaurantEntity getRestaurantByUuid(final String restaurantUuid)
        {
            // list of type restaurant Entity
            List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
        try
        {
         TypedQuery<RestaurantEntity> query  = entityManager.createQuery("SELECT r from RestaurantEntity r WHERE r.uuid =:restaurantUuid",RestaurantEntity.class);
         query.setParameter("restaurantUuid",restaurantUuid);
         restaurantEntityList = query.getResultList();
        }
        catch (Exception x)
        {
            return null;
        }
        entityManager.close();
        if(restaurantEntityList.size()==0)
            return null;
        else
            return restaurantEntityList.get(0);
        }
}
