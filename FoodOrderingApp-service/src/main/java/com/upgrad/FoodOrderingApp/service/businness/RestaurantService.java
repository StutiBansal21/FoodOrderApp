package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;

import java.util.List;

public interface RestaurantService {

    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName) throws RestaurantNotFoundException;
    public RestaurantEntity getRestaurantById(final Long restaurantId);
    public RestaurantEntity getRestaurantByUuid(final String restaurantUuid) throws RestaurantNotFoundException;
}
