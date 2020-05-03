package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantDao {
    public List<RestaurantEntity> getRestaurantsByName(final String restaurantName);
    public RestaurantEntity getRestaurantById(final Long restaurantId);
    public RestaurantEntity getRestaurantByUuid(final String restaurantUuid);
}
