package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

import java.util.List;

public interface AddressDao {
    AddressEntity saveAddress(AddressEntity addressEntity);
    //StateEntity getStateIdByUuid(final String stateUuid);
    List<AddressEntity> getAllAddresses(final CustomerEntity customerEntity);
    String getStateNameByStateId(final long stateId);
    AddressEntity deleteAddress(AddressEntity addressEntity);
    AddressEntity searchByUuid(final String addressUuid);
    AddressEntity getAddressById(final Long addressId);

}
