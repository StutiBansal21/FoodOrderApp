package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class CategoryServiceImpl implements CategoryService{

    @Autowired//provides more fine-grained control over where and how autowiring should be accomplished.
    private CategoryDaoImpl categoryDaoImpl;

    @Transactional//Describes a transaction attribute on an individual method or on a class.
    public CategoryEntity getCategoryUsingId(final Long categoryId)
    {
        return categoryDaoImpl.getCategoryUsingId(categoryId);
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public CategoryEntity getCategoryUsingUuid(final String categoryUuid) throws CategoryNotFoundException{

        if(categoryUuid.length()==0)
        {
            //if the categoryId input field is empty then this eception is raised
            throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
        }
        CategoryEntity categoryEntity=categoryDaoImpl.getCategoryUsingUuid(categoryUuid);
        if(categoryEntity==null)
        {
            //if there doesnt exist any category according to the ategory id n the table
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }
        return categoryEntity;
    }

    //this is the logic behind the getAllCategories endpoint
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public List<CategoryEntity> getAllCategories()
    {
        //this method is used to return all the categories in the table in the datbase
        return categoryDaoImpl.getAllCategories();
    }
}
