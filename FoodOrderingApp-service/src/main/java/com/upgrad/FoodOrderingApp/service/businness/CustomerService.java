package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;

public interface CustomerService {

    CustomerEntity saveCustomer(CustomerEntity customerEntity,String firstName,String lastName,String password,String email,String contactNumber) throws SignUpRestrictedException;
    CustomerAuthEntity login(String contactNumber, String password) throws AuthenticationFailedException;
    CustomerEntity getCustomer(String access_token) throws AuthorizationFailedException;
    boolean checkIfFieldIsEmpty(final CustomerEntity customerEntity);//signup
    boolean checkEmailPattern(final CustomerEntity customerEntity);//signup method
    boolean checkContactNumber(final CustomerEntity customerEntity);//signup method
    boolean checkPassword(final CustomerEntity customerEntity);//signup method
    boolean checkAuthenticationFormat(String authorization);

}
