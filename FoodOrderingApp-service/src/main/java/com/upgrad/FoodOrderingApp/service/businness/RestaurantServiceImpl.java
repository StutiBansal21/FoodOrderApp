package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
import javax.transaction.Transactional;

import java.util.List;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class RestaurantServiceImpl {

    @Autowired//provides more fine-grained control over where and how autowiring should be accomplished.
    private RestaurantDaoImpl restaurantDaoImpl;

    //this method returns the restaurant details by its Name
    @Transactional//Describes a transaction attribute on an individual method or on a class.
    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName) throws RestaurantNotFoundException
    {
        if(restaurantName.length()==0)//if the inout field is empty then this exception is raised
        {
            throw new RestaurantNotFoundException("RNF-003", "Restaurant name field should not be empty");
        }
        else
            return restaurantDaoImpl.getRestaurantsByName(restaurantName);
    }

    @Transactional
    public RestaurantEntity getRestaurantById(final Long restaurantId)
    {
        //return the resturant by its id
        return restaurantDaoImpl.getRestaurantById(restaurantId);
    }

    public RestaurantEntity getRestaurantByUuid(final String restaurantUuid) throws RestaurantNotFoundException
    {
        if (restaurantUuid.length()==0)
        {
            //if inout field is empty then this exception is raised
            throw new RestaurantNotFoundException("RNF-002", "Restaurant id field should not be empty");
        }
        RestaurantEntity entity=restaurantDaoImpl.getRestaurantByUuid(restaurantUuid);
        if (entity==null)//if there is no restaurant by this id in the database then entity is null then this exception is raised
        {
            throw new RestaurantNotFoundException("RNF-001", "No restaurant by this id");
        }
        return entity;
    }
}
