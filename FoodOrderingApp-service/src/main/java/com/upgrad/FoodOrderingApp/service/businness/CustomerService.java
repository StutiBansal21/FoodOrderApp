package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;

public interface CustomerService {

    CustomerEntity saveCustomer(CustomerEntity customerEntity,String firstName,String lastName,String password,String email,String contactNumber) throws SignUpRestrictedException;
    boolean checkIfFieldIsEmpty(final CustomerEntity customerEntity);//signup method validator function
    boolean checkEmailPattern(final CustomerEntity customerEntity);//signup method validator function
    boolean checkContactNumber(final CustomerEntity customerEntity);//signup method validator function
    boolean checkPassword(final CustomerEntity customerEntity);//signup method validator function


}
