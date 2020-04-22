package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;

import java.util.List;

public interface AddressService {

    AddressEntity saveAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity) throws
            SaveAddressException;
    AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws AuthorizationFailedException,
            AddressNotFoundException;
    List<AddressEntity> getAllAddress(CustomerEntity customer);
    StateEntity getState(String stateUUID);

}