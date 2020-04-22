package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @RequestMapping(method= RequestMethod.POST,path = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//api takes the input as Json format and output as Json format
    public ResponseEntity<SignupCustomerResponse> signUp(@RequestBody final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException{


        if(signupCustomerRequest.getLastName().isEmpty()) {
            throw new SignUpRestrictedException("SGR -005", "Last name cannot be empty");
        }

        CustomerEntity customerEntity=new CustomerEntity();//which is going to be saved in db yeh sb store hoga db mei.
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setEmailAddress(signupCustomerRequest.getEmailAddress());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setUuid(UUID.randomUUID().toString());
        //salt is to be set in service using the password cryptography
       // customerEntity.setSalt(signupCustomerRequest.getSalt()); issues with getSalt() method
        //set id?? uuid??


        final CustomerEntity responseCustomer=customerService.saveCustomer(customerEntity);//if everything is f9 then this will bring some output response
        SignupCustomerResponse signupCustomerResponse=new SignupCustomerResponse();
        signupCustomerResponse.setId(responseCustomer.getUuid());
        signupCustomerResponse.setStatus("Customer Registered");
        return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String auth) {

        //call to service to login

        //Use https://www.base64encode.org/ to create the encoded string

        //pass that encoded string in request header.

        //decode that string and get the password and contact number(On upgrad's portal there is a seperate lecture for this, see that one).

        // After decoding it check if that contact number exist in database if not then throw exception otherwise encrypt the decoded
        // password and check with password if matched then create a random string

        // create one more table customerAuthEntity and store this string and current time inside that table and customer also.

        //set that random string in response headers and return it in response.

        LoginResponse loginResponse = new LoginResponse();
        return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
    }
}
