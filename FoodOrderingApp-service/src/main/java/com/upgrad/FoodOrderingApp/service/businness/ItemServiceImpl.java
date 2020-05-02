package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemDaoImpl itemDaoImpl;

    @Override
    //@Transactional(propagation = Propagation.REQUIRED)
    @Transactional
    public ItemEntity getItemUsingId(final long itemId){

        return itemDaoImpl.getItemUsingId(itemId);
    }
}
