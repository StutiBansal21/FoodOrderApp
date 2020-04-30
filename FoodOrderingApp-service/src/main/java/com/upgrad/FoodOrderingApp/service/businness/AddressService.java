package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;

import java.util.List;

public interface AddressService {

    AddressEntity saveAddress(AddressEntity addressEntity, final String stateUuid, final CustomerEntity customerEntity) throws SaveAddressException, AddressNotFoundException;
    AddressEntity deleteAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity);
    //StateEntity getStateIdByUuid(final String stateUuid) throws AddressNotFoundException ;
    List<AddressEntity> getAllAddresses(final CustomerEntity customerEntity);
    //String getStateNameByStateId(final long stateId);
    //AddressEntity searchByUuid(final String addressUuid);
    //CustomerAddressEntity searchByAddressId(final long addressId);
    //AddressEntity getAddressById(final Long addressId);
    }
