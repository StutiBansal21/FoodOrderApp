package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.AddressServiceImpl;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerServiceImpl;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController//returns the object and object data is directly written into HTTP response as JSON
@CrossOrigin//for resolving the CORS issue when integrating the frontend and the backend
@RequestMapping("/")//to tell where the mapping in the db has to go
public class AddressController {

    @Autowired//control over where and how autowiring should be done in the code .Can be on constructors, variables class and its objects
    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    private AddressServiceImpl addressServiceImpl;


    //saveAddress endpoint definition of POST type
    @RequestMapping(path = "/address",method = RequestMethod.POST)  //,produces= MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse>saveAddress(@RequestBody SaveAddressRequest saveAddressRequest, @RequestHeader("accessToken") final String accessToken) throws AuthorizationFailedException, SaveAddressException,AddressNotFoundException, UpdateCustomerException {
        try{
         //if any of the input fields is empty then this exception is thrown
        if(saveAddressRequest.getStateUuid().isEmpty() || saveAddressRequest.getPincode().isEmpty()|| saveAddressRequest.getFlatBuildingName().isEmpty() || saveAddressRequest.getLocality().isEmpty() || saveAddressRequest.getCity().isEmpty())
        {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }
           // Getting the CustomerEntity object using the accessToken.
            CustomerEntity customerEntity=customerServiceImpl.getCustomer(accessToken);
            if(customerEntity==null)//if the accessToken is not in db
            {
                throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
            }
            if(!customerServiceImpl.isUserLoggedIn(accessToken))
            {
                //if the accessToken states that the customer has already logged out
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            if(customerServiceImpl.checkExpiryOfToken(accessToken))
            {
                //if the customer has not logged out but the max time limit to stay logged in has exceeded.
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            AddressEntity addressEntity=new AddressEntity();// Creating an empty AddressEntity object.
            //setting the values in the AddressEntity in the address table
            addressEntity.setUuid(UUID.randomUUID().toString());//this generates UUID in the db table
            addressEntity.setFlatBuilNumber(saveAddressRequest.getFlatBuildingName());//setting the flat,building value in the table
            addressEntity.setLocality(saveAddressRequest.getLocality());//setting the locality
            addressEntity.setCity(saveAddressRequest.getCity());//setting the city in the table
            addressEntity.setPincode(saveAddressRequest.getPincode());//setting the pincode according to the constraints
            String stateUuid=saveAddressRequest.getStateUuid();//the stateUuid is taken from the State table
            addressServiceImpl.saveAddress(addressEntity,stateUuid,customerEntity);//the logic of the saveAddress
            //if all conditions are met and no issues found then save the data in the table
            SaveAddressResponse response=new SaveAddressResponse().id(addressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
            return new ResponseEntity<SaveAddressResponse>(response, HttpStatus.CREATED);
        }
        catch (AuthorizationFailedException e)//if there exists exception in the table
        {
            //throw the exception
           SaveAddressResponse errorResponse=new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
           return new ResponseEntity<SaveAddressResponse>(errorResponse,HttpStatus.BAD_REQUEST);
        }
        catch (SaveAddressException e)
        {
            SaveAddressResponse errorResponse=new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<SaveAddressResponse>(errorResponse,HttpStatus.BAD_REQUEST);
        }
        catch (AddressNotFoundException e)
        {
            SaveAddressResponse errorResponse=new SaveAddressResponse().id(e.getCode()).status(e.getErrorMessage());
            return new ResponseEntity<SaveAddressResponse>(errorResponse,HttpStatus.BAD_REQUEST);
        }

    }

    //getAddress From the db table endpoint definition which is of GET type
    @RequestMapping(method = RequestMethod.GET,value = "/address/customer",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllAddress(@RequestHeader("accessToken") String accessToken){
        try {
            CustomerEntity customerEntity=customerServiceImpl.getCustomer(accessToken);

            if(customerEntity==null)
            {
                //if the accessToken doesnt exist in the db table
                throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
            }
            if(!customerServiceImpl.isUserLoggedIn(accessToken))
            {
                //if the accessToken states that the customer has already logged out
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            if(customerServiceImpl.checkExpiryOfToken(accessToken))
            {
                //if the customer has not logged out but the max time limit to stay logged in has exceeded.
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            //getting the object list of AddressEntity
            List<AddressEntity> addressEntityList=addressServiceImpl.getAllAddresses(customerEntity);
            List<AddressList> newAddressEntityList=new ArrayList<>();//empty AddressList

            // Creating an AddressListState object.
            for(AddressEntity addressEntity:addressEntityList){
                AddressListState addressListState=new AddressListState().id(UUID.fromString(addressEntity.getUuid())).stateName(addressServiceImpl.getStateNameByStateId(addressEntity.getId()));
                // Creating an AddressList object.
            AddressList addressList=new AddressList().id(UUID.fromString(addressEntity.getUuid())).flatBuildingName(addressEntity.getFlatBuilNumber()).locality(addressEntity.getLocality()).city(addressEntity.getCity()).state(addressListState).pincode(addressEntity.getPincode());
            newAddressEntityList.add(addressList);//adding AddressList object to the list.
            }
            AddressListResponse addressListResponse=new AddressListResponse().addresses(newAddressEntityList);
            return new ResponseEntity(addressListResponse,HttpStatus.OK);
        }
        catch (AuthorizationFailedException e)
        {
            ErrorResponse response=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
        }
    }

    //deleteAddress endpoint definition with DELETE type method
        @RequestMapping(value = "/address/{address_id}",method=RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity deleteAddress(@PathVariable("address_id")final String addressUuid,@RequestHeader("accessToken")final String accessToken)
    {
        try {
            //boolean check=false;//creating a flag to check the values
            CustomerEntity customerEntity=customerServiceImpl.getCustomer(accessToken);
            if(customerEntity == null) {
                //if the accessToken doesnt exist in the db table
                throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
            }
            if(!customerServiceImpl.isUserLoggedIn(accessToken)) {
                //if the accessToken states that the customer has already logged out
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            if(customerServiceImpl.checkExpiryOfToken(accessToken)) {
                //if the customer has not logged out but the max time limit to stay logged in has exceeded.
                throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
            }
            //if(addressUuid.length()==0)//required hai
            if(addressUuid.isEmpty())
            {
                throw new AddressNotFoundException("ANF-005", "Address id can not be empty");
            }
            AddressEntity addressEntity=addressServiceImpl.searchByUuid(addressUuid);
            CustomerAddressEntity customerAddressEntity=addressServiceImpl.searchByAddressId(addressEntity.getId());

            if(customerAddressEntity.getCustomerId()!=customerEntity.getId())//nhi chl rhi
           // if(!check)
            {
                //if the customer id of the table doesnt match with the id of the accessToken customer then this exception is thrown
                throw new AuthorizationFailedException("ATHR-004","You are not authorized to view/update/delete any one else's address.");
            }
            if(addressEntity == null)//nhi chl rhi
            {
                throw new AddressNotFoundException("ANF-003", "No address by this id");
            }
            addressServiceImpl.deleteAddress(addressEntity,customerAddressEntity);// Getting the AddressEntity object using the addressUuid.
            DeleteAddressResponse response=new DeleteAddressResponse().id(UUID.fromString(addressEntity.getUuid())).status("ADDRESS DELETED SUCCESSFULLY");
            return new ResponseEntity<DeleteAddressResponse>(response,HttpStatus.OK);

        } catch (AuthorizationFailedException e) {
            //if some constraints are not followed exception is thrown which is caught by catch block
            ErrorResponse errorResponse=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }
        catch (AddressNotFoundException e) {
            ErrorResponse errorResponse=new ErrorResponse().code(e.getCode()).message(e.getErrorMessage());
            return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
        }

    }

}

