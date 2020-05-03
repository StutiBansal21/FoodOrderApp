package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;

import java.util.List;

public interface RestaurantCategoryService {
    public List<RestaurantCategoryEntity> getCategoriesUsingRestaurantId(final Long restaurantId);
    public List<RestaurantCategoryEntity> getRestaurantsUsingCategoryId(final Long categoryId);
}
