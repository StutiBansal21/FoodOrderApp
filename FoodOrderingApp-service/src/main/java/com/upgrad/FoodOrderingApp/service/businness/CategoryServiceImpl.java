package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl {

    @Autowired
    private CategoryDaoImpl categoryDaoImpl;

    @Transactional
    public CategoryEntity getCategoryUsingId(final Long categoryId)
    {
        return categoryDaoImpl.getCategoryUsingId(categoryId);
    }

    @Transactional
    public CategoryEntity getCategoryUsingUuid(final String categoryUuid) throws CategoryNotFoundException{

        if(categoryUuid.length()==0)
        {
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        CategoryEntity categoryEntity=categoryDaoImpl.getCategoryUsingUuid(categoryUuid);
        if(categoryEntity==null)
        {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }
        return categoryEntity;
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public List<CategoryEntity> getAllCategories()
    {

        return categoryDaoImpl.getAllCategories();
    }
}
