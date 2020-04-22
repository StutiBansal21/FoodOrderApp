package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

import java.util.List;

public interface AddressDao {
    AddressEntity saveAddress(AddressEntity addressEntity);
    AddressEntity getAddressByUUID(String addressId);
    CustomerAddressEntity getCustomerByAddress(String addressId);
    CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity);
    AddressEntity deleteAddress(AddressEntity addressEntity);
    List<AddressEntity> getAllAddress(CustomerEntity customer);
    StateEntity getStateByUUID(String uuid);
}
