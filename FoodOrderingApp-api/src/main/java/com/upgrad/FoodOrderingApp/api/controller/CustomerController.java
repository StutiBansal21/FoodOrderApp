package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.awt.*;
import java.util.Base64;
import java.util.UUID;

@RestController//returns the object and object data is directly written into HTTP response as JSON
@CrossOrigin//for the future use if we want t0 link the frontend to the backend and to avodi the CORS issue
@RequestMapping("/")//to tell where the mapping in the db has to go
public class CustomerController {

    @Autowired//control over where and how autowiring should be done in the code .Can be on constructors, variables class and its objects
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    //this is the signup endpoint function definition which is of POST type
    @RequestMapping(method= RequestMethod.POST,path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//api takes the input as Json format and output as Json format
    public ResponseEntity<SignupCustomerResponse> signUp(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException{

       final CustomerEntity customerEntity=new CustomerEntity();//which is going to be saved in db yeh sb store hoga db mei.
        customerEntity.setUuid(UUID.randomUUID().toString());//this generates random uuid automatically .the intellij has the function
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());//to set the firstname in the db
        customerEntity.setLastName(signupCustomerRequest.getLastName());//set the lastname in the db
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());//set the contact number
        customerEntity.setEmailAddress(signupCustomerRequest.getEmailAddress());//set the email id
        customerEntity.setPassword(signupCustomerRequest.getPassword());//set the password
        customerEntity.setSalt("1234");//salt is for password cryptography and security reasons.

        try {
            final CustomerEntity responseCustomer = customerService.saveCustomer(customerEntity, signupCustomerRequest.getFirstName(), signupCustomerRequest.getLastName(), signupCustomerRequest.getContactNumber(), signupCustomerRequest.getEmailAddress(), signupCustomerRequest.getPassword());//if everything is f9 then this will bring some output response
            SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse();
            signupCustomerResponse.setId(responseCustomer.getUuid());
            signupCustomerResponse.setStatus("CUSTOMER SUCCESSFULLY REGISTERED");//if no issues then customer is stored in db and this msg displayed
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
            }
        catch(SignUpRestrictedException e) {
            SignupCustomerResponse signupCustomerResponse=new SignupCustomerResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse,HttpStatus.BAD_REQUEST);//if issue the exception is thrown and the return is from the exception classes
        }
    }

}
