package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;

import java.util.List;

public interface RestaurantCategoryDao {

    public List<RestaurantCategoryEntity> getCategoriesByRestaurantId(final long retaurantId);

    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final long categoryId);
}
