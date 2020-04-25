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

@RestController
@CrossOrigin
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(method= RequestMethod.POST,path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//api takes the input as Json format and output as Json format
    public ResponseEntity<SignupCustomerResponse> signUp(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException{

       final CustomerEntity customerEntity=new CustomerEntity();//which is going to be saved in db yeh sb store hoga db mei.
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setEmailAddress(signupCustomerRequest.getEmailAddress());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setSalt("1234");

        try {
            final CustomerEntity responseCustomer = customerService.saveCustomer(customerEntity, signupCustomerRequest.getFirstName(), signupCustomerRequest.getLastName(), signupCustomerRequest.getContactNumber(), signupCustomerRequest.getEmailAddress(), signupCustomerRequest.getPassword());//if everything is f9 then this will bring some output response
            SignupCustomerResponse signupCustomerResponse = new SignupCustomerResponse();
            signupCustomerResponse.setId(responseCustomer.getUuid());
            signupCustomerResponse.setStatus("CUSTOMER SUCCESSFULLY REGISTERED");
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.CREATED);
            }
        catch(SignUpRestrictedException e) {
            SignupCustomerResponse signupCustomerResponse=new SignupCustomerResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse,HttpStatus.BAD_REQUEST);
        }
    }
    //login method
    /*@RequestMapping(method = RequestMethod.POST,path = "/customer/login",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse>login(@RequestHeader("authorization")final String authorization)throws AuthenticationFailedException{

        byte decode[]= Base64.getDecoder().decode(authorization.split("Basic")[1]);
        String decodedString=new String(decode);
        String decodeArray[]=decodedString.split(":");
        if(customerService.checkAuthenticationFormat(authorization)==true)

                    final CustomerAuthEntity customerAuthEntity=customerService.authenticate(decodeArray[0],decodeArray[1]);

        CustomerEntity customerEntity=customerService.searchBy

    }*/

}
