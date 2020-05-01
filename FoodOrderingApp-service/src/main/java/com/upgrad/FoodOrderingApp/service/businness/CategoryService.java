package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {

    public CategoryEntity getCategoryUsingUuid(final String categoryUuid) throws CategoryNotFoundException;
    public List<CategoryEntity> getAllCategories();
}
