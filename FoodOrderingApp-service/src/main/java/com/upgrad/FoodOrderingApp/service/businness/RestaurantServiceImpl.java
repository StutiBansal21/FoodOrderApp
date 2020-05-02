package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.RestaurantDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // class that provide some business functionality , used to mark class as a service provider
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantDaoImpl restaurantDaoImpl;


    //function getRestaurantByName and return list type of RestaurantEntity
    @Transactional// means of telling your code when it executes that it must have a Transaction.
    public List<RestaurantEntity> getRestaurantUsingName(final String rName) throws RestaurantNotFoundException
    {
        if (rName.length()==0) // if length of restaurant name is 0 then throw exception
        {
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }
        else
        {
            //if it validates then provide value using function defined in restaurantDao
            return restaurantDaoImpl.getRestaurantUsingName(rName);
        }
    }

    @Transactional
    public  RestaurantEntity getRestaurantUsingId(final long rId)
    {
        return restaurantDaoImpl.getRestaurantUsingId(rId); // return value using getRestaurantUsingId defined in restaurantDao
    }

    @Transactional
    public RestaurantEntity getRestaurantUsingUuid(final String rUuid) throws RestaurantNotFoundException
    {
        if(rUuid.length()==0) // if length of restaurantUuid is 0 then throw exception
        {
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }
        RestaurantEntity restaurantEntity =restaurantDaoImpl.getRestaurantByUuid(rUuid); // object of RestaurantEntity
        if (restaurantEntity== null) // if value of restaurantEntity is null then throw exception
            throw   new RestaurantNotFoundException("RNF-001","No restaurant by this id");

        return restaurantEntity;
    }
}
