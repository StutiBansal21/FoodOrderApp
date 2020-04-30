package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerServiceImpl;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController//returns the object and object data is directly written into HTTP response as JSON
@CrossOrigin//for the future use if we want t0 link the frontend to the backend and to avoid the CORS issue
@RequestMapping("/")//to tell where the mapping in the db has to go
public class CustomerController {

    @Autowired//control over where and how autowiring should be done in the code .Can be on constructors, variables class and its objects
    private CustomerService customerService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

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
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse,HttpStatus.CREATED);
            //return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse, HttpStatus.OK);
            }
        catch(SignUpRestrictedException e) {
            SignupCustomerResponse signupCustomerResponse=new SignupCustomerResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<SignupCustomerResponse>(signupCustomerResponse,HttpStatus.BAD_REQUEST);//if issue the exception is thrown and the return is from the exception classes
        }
    }


    //login endpoint functionality defined having method of post typ
    @RequestMapping(method = RequestMethod.POST,path="/customer/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse>login(@RequestHeader("authorization")final String authorization)throws AuthenticationFailedException {
//the String authorization has the contactnumber:password which is encoded in base64 format which is then passed along
        byte[] decodedBytes = Base64.getDecoder().decode(authorization);//.split("Basic ")[1]);//splits the bearer
        String decodedString = new String(decodedBytes);
        if (!decodedString.contains(":"))//if the encrypted form doesnt have : then tht means the inout string/value is wrong
            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
        try {
            String decodeArr[] = decodedString.split(":");//splits contact number and password as in betwee the 2=string of them is :
                final CustomerAuthEntity customerAuthEntity = customerServiceImpl.verifyAuthenticate(decodeArr[0], decodeArr[1]);//passing the contactnumber and password as array 0 and array 1
            CustomerEntity customerEntity=customerServiceImpl.searchById(customerAuthEntity.getCustomerId());
            //CustomerEntity customerEntity = customerAuthEntity.getCustomer();//return customer according to the query
                if (customerEntity == null) {
                    //if the returned customerEntity is null ie returns nulll then thsi exception with the error message is thrown
                    throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
                }
                LoginResponse loginResponse = new LoginResponse().firstName(customerEntity.getFirstName()).lastName(customerEntity.getLastName()).contactNumber(customerEntity.getContactNumber()).emailAddress(customerEntity.getEmailAddress()).id(customerEntity.getUuid())
                        .message("LOGGED IN SUCCESSFULLY"); // if all validations verified then customer login successfully
                HttpHeaders headers = new HttpHeaders();
            headers.add("access-token", customerAuthEntity.getAccessToken());
               /* List<String> header = new ArrayList<>();
                header.add("access-token");
                headers.setAccessControlExposeHeaders(header);
                headers.add("access-token", customerAuthEntity.getAccessToken());*/
                return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);// if everything is verified then it return response and HTTPstatus.OK
        } catch (AuthenticationFailedException e) {
            LoginResponse loginresponse = new LoginResponse().id(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<LoginResponse>(loginresponse, HttpStatus.UNAUTHORIZED);
        }
    }

    //this is logout endpoint method definition whch is of post type and throws AuthorizationFailed exception
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)//,consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("accessToken") final String accessToken) throws AuthorizationFailedException{
        try {
            //String[] aToken = accessToken.split("Bearer ");//splits the accesstoken which we got during the login and then checks the condtitions
            CustomerAuthEntity customerAuthEntity = customerServiceImpl.logout(accessToken);//method logout is called which holds the business logic
            // if every validation fullfill then the we send this message to customer
            LogoutResponse response = new LogoutResponse().id(customerAuthEntity.getUuid()).message("LOGGED OUT SUCCESSFULLY");
            return new ResponseEntity<LogoutResponse>(response, HttpStatus.OK);
        }
        catch(AuthorizationFailedException e)//if above validations conditions dont hold true then exception is raised which is caught in this block
        {
            LogoutResponse logoutResponse=new LogoutResponse().id(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity<LogoutResponse>(logoutResponse,HttpStatus.BAD_REQUEST);
        }
    }

    //this is the change password endpoint which is of put type
    @RequestMapping(path = "/customer/password",method = RequestMethod.PUT)//,produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse>updateCustomerPassword(UpdatePasswordRequest updatePasswordRequest, @RequestHeader("accessToken")final String accessToken) throws UpdateCustomerException ,AuthorizationFailedException{
        try{
            //String bToken[]=bearerAuthorization.split("Bearer");//this split the access token which we get when we log in
            CustomerEntity customerEntity = customerServiceImpl.getCustomer(accessToken);//this method is to bring the customer from the customer_Auth table having the same accessToken
            CustomerEntity customerEntityNew = customerServiceImpl.updateCustomerPassword(updatePasswordRequest.getOldPassword(),updatePasswordRequest.getNewPassword(),customerEntity);
            //final CustomerEntity customerEntity=customerService.updateCustomerPassword(bToken[1],oldPassword,newPassword);//the method which holds the logic in service class
            UpdatePasswordResponse updatePasswordResponse=new UpdatePasswordResponse().id(customerEntity.getUuid()).status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");
            return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);//if all hold true then this works with the above output
           }
        catch (AuthorizationFailedException e)
        {
            UpdatePasswordResponse response=new UpdatePasswordResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<UpdatePasswordResponse>(response,HttpStatus.BAD_REQUEST);
        }
        catch (UpdateCustomerException e1)//if the password validations are not true then the updateCustomerException is thrown which is caught by this
        {
            UpdatePasswordResponse response=new UpdatePasswordResponse().id(e1.getCode()).status(e1.getErrorMessage());//this returns the error code with the error message
            return new ResponseEntity<UpdatePasswordResponse>(response,HttpStatus.BAD_REQUEST);
        }

    }
}
