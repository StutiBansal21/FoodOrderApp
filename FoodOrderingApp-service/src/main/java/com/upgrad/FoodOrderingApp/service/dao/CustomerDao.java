package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;

public interface CustomerDao {

    CustomerEntity saveCustomer(CustomerEntity customerEntity);


    CustomerEntity getCustomer(String contactNumber);

    boolean getCustomer(boolean b);

    CustomerAuthEntity getCustomerAuthByAccesstoken(String access_token);


    //boolean getCustomer(boolean b);
}
