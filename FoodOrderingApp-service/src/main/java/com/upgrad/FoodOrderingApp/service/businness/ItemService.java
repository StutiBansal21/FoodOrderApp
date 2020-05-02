package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;

public interface ItemService {
    public ItemEntity getItemUsingId(final long itemId);
}
