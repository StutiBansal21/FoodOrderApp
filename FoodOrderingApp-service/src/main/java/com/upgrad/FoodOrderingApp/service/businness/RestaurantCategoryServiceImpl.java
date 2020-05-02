package com.upgrad.FoodOrderingApp.service.businness;


import com.upgrad.FoodOrderingApp.service.dao.RestaurantCategoryDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service// defines that class provides some business fuctionality, used to mark class as a service provider
public class RestaurantCategoryServiceImpl implements  RestaurantCategoryService{

    @Autowired //control where and how autowiring should be done in the code , it can be constructors , variable class etc
    private RestaurantCategoryDaoImpl restaurantCategoryDao;

    @Transactional// ask your code that this function executes must have a transaction
    public List<RestaurantCategoryEntity> getCategoryByRestaurantId(final long rId)
    {
        return restaurantCategoryDao.getCategoriesByRestaurantId(rId); // return value of category using restaurantId
    }

    @Transactional
    public List<RestaurantCategoryEntity> getRestaurantByCategoryId(final long cid)
    {
        return restaurantCategoryDao.getRestaurantByCategoryId(cid); // return restaurant using categoryId
    }
}
