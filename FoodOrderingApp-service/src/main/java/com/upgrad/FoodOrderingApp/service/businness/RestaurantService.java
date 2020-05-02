package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    public List<RestaurantEntity> getRestaurantUsingName(final String rName) throws RestaurantNotFoundException;

    public  RestaurantEntity getRestaurantUsingId(final long rId);

    public RestaurantEntity getRestaurantUsingUuid(final String rUuid) throws RestaurantNotFoundException;
}
