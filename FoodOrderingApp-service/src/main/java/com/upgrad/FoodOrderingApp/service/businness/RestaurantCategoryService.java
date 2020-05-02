package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;

import java.util.List;

public interface RestaurantCategoryService {

    public List<RestaurantCategoryEntity> getCategoryByRestaurantId(final long rId);

    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final long cid);
}
