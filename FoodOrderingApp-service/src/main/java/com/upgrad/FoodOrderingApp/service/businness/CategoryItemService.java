package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;

import java.util.List;

public interface CategoryItemService {
    public List<CategoryItemEntity> getItemsUsingCategoryId(final long categoryId);
}
