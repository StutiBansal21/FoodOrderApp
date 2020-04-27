/*
package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity) throws
            SaveAddressException {
        //validations

        if(addressEntity.getPincode().isEmpty() || addressEntity.getFlatBuilNo().isEmpty()) {
            throw new SaveAddressException("SAR-001", "No Fiels Can be empty");
        }

        addressDao.saveAddress(addressEntity);
        saveCustomerAddress(customerAddressEntity);
        return  addressEntity;
    }

    @Transactional
    public AddressEntity saveAddress(AddressEntity addressEntity,final String stateUuid,final CustomerEntity customerEntity) throws SaveAddressException,AddressNotFoundException
    {
      if(!checkFields(addressEntity,stateUuid))
      {
        throw  new SaveAddressException("SAR-001","No field can be empty");
      }
      else if(!verifyPincode(addressEntity))
      {
          throw new SaveAddressException("SAR-002","Invalid pincode");
      }
      else
      {
        StateEntity stateEntity =  getState(stateUuid);
        if(stateEntity == null)
        {
            throw new AddressNotFoundException("ANF-002","No state by this id");
        }
        addressEntity.setState(stateEntity.getId());
        addressEntity =addressDao.saveAddress(addressEntity);


      }
    }


    @Override
    public AddressEntity getAddressByUUID(String addressId, CustomerEntity customerEntity) throws
            AuthorizationFailedException, AddressNotFoundException {
        //   if(addressDao.getAddressByUUID(addressId) == null) throw new AddressNotFoundException("ANF-003", "No address by this id");
        //   else if(addressDao.getCustomerByAddress(addressId).getCustomer() != customerEntity) throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        return addressDao.getAddressByUUID(addressId);
    }



    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAddressEntity saveCustomerAddress(CustomerAddressEntity customerAddressEntity) {
        return addressDao.saveCustomerAddress(customerAddressEntity);
    }


    @Override
    public List<AddressEntity> getAllAddress(CustomerEntity customer)  {
        return addressDao.getAllAddress(customer);
    }

    @Override
    public StateEntity getState(String stateUUID) {
        return addressDao.getStateByUUID(stateUUID);
    }


}
*/
