package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;

public interface CustomerAuthEntityDao {
    CustomerAuthEntity create(final CustomerAuthEntity customerAuthEntity);
    CustomerAuthEntity updateCustomer(final CustomerAuthEntity customerAuthEntity);
    CustomerAuthEntity getAuthTokenByAccessToken(final String accessToken);
}
