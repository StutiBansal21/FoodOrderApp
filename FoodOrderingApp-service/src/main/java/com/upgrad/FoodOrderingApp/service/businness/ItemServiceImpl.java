package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDaoImpl itemDaoImpl;

    @Override
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional//Describes a transaction attribute on an individual method or on a class.
    public ItemEntity getItemUsingId(final long itemId){
        //this method is for getting the items according to the itemid
        return itemDaoImpl.getItemUsingId(itemId);
    }
}
