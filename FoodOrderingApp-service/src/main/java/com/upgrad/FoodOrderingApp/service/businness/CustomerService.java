package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;

public interface CustomerService {

    CustomerEntity saveCustomer(CustomerEntity customerEntity,String firstName,String lastName,String password,String email,String contactNumber) throws SignUpRestrictedException;
    boolean checkIfFieldIsEmpty(final CustomerEntity customerEntity);//signup method validator function
    boolean checkEmailPattern(final CustomerEntity customerEntity);//signup method validator function
    boolean checkContactNumber(final CustomerEntity customerEntity);//signup method validator function
    boolean checkPassword(final CustomerEntity customerEntity);//signup method validator function
    CustomerAuthEntity verifyAuthenticate(String contactNumber,String password)throws AuthenticationFailedException;//login method
   CustomerEntity getCustomer(final String accessToken) throws AuthorizationFailedException;//login method validator
    //CustomerAuthEntity authorization(String access_token) throws AuthorizationFailedException;//logout method
    CustomerAuthEntity logout(final String accesstoken)throws AuthorizationFailedException;//logout
    //public CustomerEntity getCustomer (final String accessToken) throws AuthorizationFailedException;
    CustomerEntity updateCustomerPassword(String oldPassword,String newPassword,CustomerEntity customerEntity)throws UpdateCustomerException;
}
