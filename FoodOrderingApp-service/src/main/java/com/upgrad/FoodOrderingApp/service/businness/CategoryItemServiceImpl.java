package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryItemDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class CategoryItemServiceImpl implements CategoryItemService{

    @Autowired//provides more fine-grained control over where and how autowiring should be accomplished.
    private CategoryItemDaoImpl categoryItemDaoImpl;


    //this mwthod retruns the items in the database according to the categoryId under which it belongs to
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional//Describes a transaction attribute on an individual method or on a class.
    public List<CategoryItemEntity> getItemsUsingCategoryId(final long categoryId){
        return categoryItemDaoImpl.getItemsUsingCategoryId(categoryId);
    }

}
