package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;

public interface CustomerDao {
    //logic how customer is saved in db
    CustomerEntity saveCustomer(CustomerEntity customerEntity);
    //CustomerEntity getCustomer(CustomerEntity customerEntity);
    CustomerEntity getCustomerByContactNumber(String contactNumber);
    //fetch the contactnumber se customer.if exist
    //CustomerEntity getCustomer(String contactNumber);
    //boolean getCustomer(boolean b);
    //CustomerAuthEntity getAuthTokenByAccessToken(String access_token);

}
