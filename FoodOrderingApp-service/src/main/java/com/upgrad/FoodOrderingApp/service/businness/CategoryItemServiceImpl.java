package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryItemDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryItemServiceImpl {

    @Autowired
    private CategoryItemDaoImpl categoryItemDaoImpl;


    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public List<CategoryItemEntity> getItemsUsingCategoryId(final long categoryId){
        return categoryItemDaoImpl.getItemsUsingCategoryId(categoryId);
    }

}
