package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;

import java.util.List;

public interface RestaurantCategoryDao {

    public List<RestaurantCategoryEntity> getCategoriesUsingRestaurantId(final Long restaurantId);
    public List<RestaurantCategoryEntity> getRestaurantsUsingCategoryId(final Long categoryId);
}
