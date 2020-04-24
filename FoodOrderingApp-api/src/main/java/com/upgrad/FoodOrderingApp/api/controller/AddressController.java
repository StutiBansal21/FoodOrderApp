package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.service.businness.AddressService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.FoodOrderingApp.api.model.*;

import java.util.List;
import java.util.UUID;


@RestController
public class AddressController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.POST, path = "/address", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveAddressResponse> saveAddress(@RequestBody(required = false) final SaveAddressRequest saveAddressRequest, @RequestHeader("authorization") final String randomString) throws
            AuthorizationFailedException, SaveAddressException, AddressNotFoundException {
        String authorization = null;
        String access_token = authorization.split("Bearer ")[1];

        //validate the user first using this
        CustomerEntity customerEntity = customerService.getCustomer(randomString);

        final AddressEntity addressEntity = new AddressEntity();

        addressEntity.setFlatBuilNo(saveAddressRequest.getFlatBuildingName());
        addressEntity.setLocality(saveAddressRequest.getLocality());
        addressEntity.setCity(saveAddressRequest.getCity());
        addressEntity.setPincode(saveAddressRequest.getPincode());
        // addressEntity.setState(addressService.getStateByUUID(saveAddressRequest.getStateUuid()));
        addressEntity.setUuid(UUID.randomUUID().toString());

        final CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setAddress(addressEntity);
        customerAddressEntity.setCustomer(customerEntity);

        final AddressEntity createdAddressEntity = addressService.saveAddress(addressEntity,customerAddressEntity);
        SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(createdAddressEntity.getUuid()).status("ADDRESS SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SaveAddressResponse>(saveAddressResponse, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/address/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddressListResponse> getAllAddresses(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {
        String access_token = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(access_token);
        List<AddressEntity> addressEntityList  = addressService.getAllAddress(customerEntity);
        AddressListResponse addressListResponse = new AddressListResponse();
        for (AddressEntity addressEntity : addressEntityList){
            AddressList addressList = new AddressList();
            addressList.setId(UUID.fromString(addressEntity.getUuid()));
            addressList.setLocality(addressEntity.getLocality());
            addressList.setCity(addressEntity.getCity());
            addressList.setFlatBuildingName(addressEntity.getFlatBuilNo());
            addressList.setPincode(addressEntity.getPincode());
            AddressListState addressListState = new AddressListState();
            addressListState.setId(UUID.fromString(addressEntity.getState().getUuid()));
            addressListState.setStateName(addressEntity.getState().getStateName());
            addressList.setState(addressListState);
            addressListResponse.addAddressesItem(addressList);
        }
        return new ResponseEntity<AddressListResponse>(addressListResponse, HttpStatus.OK);
    }


}
