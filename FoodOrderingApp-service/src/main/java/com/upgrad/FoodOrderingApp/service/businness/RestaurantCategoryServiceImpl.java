package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class RestaurantCategoryServiceImpl implements RestaurantCategoryService{

    @Autowired//provides more fine-grained control over where and how autowiring should be accomplished.
    private RestaurantCategoryDaoImpl restaurantCategoryDaoImpl;


    @Transactional//Describes a transaction attribute on an individual method or on a class.
    public List<RestaurantCategoryEntity> getCategoriesUsingRestaurantId(final Long restaurantId)
    {
        //returns the categories using the restaurantId which is particualr for each retaurant
        return restaurantCategoryDaoImpl.getCategoriesUsingRestaurantId(restaurantId);
    }

    @Transactional
    public List<RestaurantCategoryEntity> getRestaurantsUsingCategoryId(final Long categoryId){
        //returns all the restaurants using a categoryId
        return restaurantCategoryDaoImpl.getRestaurantsUsingCategoryId(categoryId);
    }
}
