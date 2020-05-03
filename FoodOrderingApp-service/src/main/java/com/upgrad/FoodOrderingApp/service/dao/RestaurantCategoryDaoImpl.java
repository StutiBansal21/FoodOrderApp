package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class RestaurantCategoryDaoImpl implements RestaurantCategoryDao{

    @PersistenceUnit//defines a set of all entity classes that are managed by EntityManager instances in an application
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext//set of entities such that for any persistent identity there is a unique entity instance.
    EntityManager entityManager;


    //this method searches for the categories using the restaurant id(uuid)
    public List<RestaurantCategoryEntity> getCategoriesUsingRestaurantId(final Long restaurantId){
        List<RestaurantCategoryEntity>list=new ArrayList<>();
        try {
            //the query is written which searches for the required in the database
            TypedQuery<RestaurantCategoryEntity> query = entityManager.createQuery("SELECT r FROM RestaurantCategoryEntity r WHERE r.restaurantId = :restaurantId", RestaurantCategoryEntity.class);
            query.setParameter("restaurantId", restaurantId);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();
        return list;
    }


    //this method searches for ll the restaurants using the categoryID
    public List<RestaurantCategoryEntity> getRestaurantsUsingCategoryId(final Long categoryId)
    {
        List<RestaurantCategoryEntity>list=new ArrayList<>();
        try {
            //the query is written which searches for the required in the database
            TypedQuery <RestaurantCategoryEntity> query = entityManager.createQuery("SELECT r FROM RestaurantCategoryEntity r WHERE r.categoryId = :categoryId", RestaurantCategoryEntity.class);
            query.setParameter("categoryId", categoryId);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();
        /*if(list.size()==0)
            return null;
        else
            return list.get(0);*/
        return list;
    }


}
