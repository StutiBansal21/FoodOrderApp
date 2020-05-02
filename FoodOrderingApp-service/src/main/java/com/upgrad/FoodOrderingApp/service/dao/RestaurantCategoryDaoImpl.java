package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository // indicates that the class provide mechanism for storage  , retreival , search and delete operation on objects
public class RestaurantCategoryDaoImpl implements RestaurantCategoryDao{

    @PersistenceContext // set of entity such that for any persistent identity there is unique identity instance
    private EntityManager entityManager;


      // function to get the categories using restaurant id
    public List<RestaurantCategoryEntity> getCategoriesByRestaurantId(final long retaurantId)
    {
        // list of type RestaurantCategoryEntity
        List<RestaurantCategoryEntity> restaurantCategoryEntityList = new ArrayList<>();
        try
        {
            // query written for our requirement using database
            TypedQuery<RestaurantCategoryEntity> query= entityManager.createQuery(" SELECT r FROM RestaurantCategoryEntity r WHERE r.restaurantId =:restaurantId",RestaurantCategoryEntity.class);
            query.setParameter("restaurantId",retaurantId);
            restaurantCategoryEntityList = query.getResultList();
        }
        catch (Exception e)
        {
            return null;
        }
        entityManager.close(); // closes an entity manager to release its persistence context and other resources
        return restaurantCategoryEntityList;
    }

    //function to getRestaurantBy the categoryId
    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final long categoryId)
    {
        List<RestaurantCategoryEntity> restaurantCategoryEntityList = new ArrayList<>();
        try
        {
            TypedQuery<RestaurantCategoryEntity> query = entityManager.createQuery("SELECT r FROM RestaurantCategoryEntity r WHERE r.categoryId = :categoryId",RestaurantCategoryEntity.class);
            query.setParameter("categoryId",categoryId);
            restaurantCategoryEntityList = query.getResultList();
        }
        catch (Exception ex)
        {
            return null;
        }
        entityManager.close();
        return restaurantCategoryEntityList;
    }
}
