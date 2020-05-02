package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantDao {

    public RestaurantEntity getRestaurantUsingId(final long restaurantId);

    public List<RestaurantEntity> getRestaurantUsingName(final String restaurantName);

    public RestaurantEntity getRestaurantByUuid(final String restaurantUuid);
}
