package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.AddressDaoImpl;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAddressEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private AddressDaoImpl addressDaoImpl;

    @Autowired
    private CustomerAddressDaoImpl customerAddressDaoImpl;

    //saveAddress implementation and the business logic
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, String stateUuid, CustomerEntity customerEntity) throws SaveAddressException, AddressNotFoundException {

            //if(checkFieldIsEmpty(addressEntity,stateUuid)){
        //if(checkFieldIsEmpty(addressEntity, stateUuid)) {
        if(addressEntity.getFlatBuilNumber().length()==0||addressEntity.getCity().length()==0||addressEntity.getLocality().length()==0||addressEntity.getPincode().length()==0||stateUuid.length()==0)
        {//if one of the fields is empty then this exception is thrown
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }
        else if(!checkIfPincode(addressEntity))
        {
            //if the pincode doesnt match with the constraints given
            throw new SaveAddressException("SAR-002","Invalid pincode");
        }
        else
        {
            StateEntity stateEntity=getStateIdByUuid(stateUuid);//getting the stateuuid from the state table
            if(stateEntity==null)//if no such stateUuid exists then this particular exception is thrown
            {
                throw new AddressNotFoundException("ANF-002", "No state by this id");
            }
            addressEntity.setStateId(stateEntity.getId());
            addressEntity=addressDao.saveAddress(addressEntity);

            CustomerAddressEntity customerAddressEntity=new CustomerAddressEntity();//creating the object
            customerAddressEntity.setCustomerId(customerEntity.getId());
            customerAddressEntity.setAddressId(addressEntity.getId());
            //CustomerAddressDaoImpl.saveCustomerAddress(customerAddressEntity);
            customerAddressDaoImpl.saveCustomerAddress(customerAddressEntity);
            return addressEntity;
        }
    }
    //function to check if the input field is filled or empty
    public boolean checkFieldIsEmpty(final AddressEntity addressEntity,final String stateUuid)
    {
       /* if(addressEntity.getFlatBuilNumber().length() == 0 || addressEntity.getLocality().length() == 0 || addressEntity.getCity().length() == 0 || addressEntity.getPincode().length()==0||stateUuid.length()==0)
        {
           return true;
        }
        else
            return false;*/

        //the constraint was that when saving the addresses in the table no field should be empty
        if (addressEntity.getFlatBuilNumber().isEmpty() || addressEntity.getLocality().isEmpty() || addressEntity.getCity().isEmpty() || addressEntity.getPincode().isEmpty() || addressEntity.getUuid().isEmpty()){
        //if(addressEntity.getFlatBuilNumber().length() == 0 || addressEntity.getLocality().length() == 0 ||addressEntity.getCity().length() == 0 || addressEntity.getPincode().length() == 0 || stateUuid.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
    //function to check if the pincode constraint is handled or not
    public boolean checkIfPincode(final AddressEntity addressEntity)
    {
        //the pincode should be of numbers and should be of length 6 only.
       /* String pattern="^[0-9]{6}$";
        if(addressEntity.getPincode().matches(pattern))
        {
            return true;
        }
        else
        {
            return false;
        }*/
        final String pin=addressEntity.getPincode();
        try
        {
            long no=Long.parseLong(pin);
            if(pin.length()!=6)
                return false;
            else
                return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }

    //this is the business logic of deleting the address of the specified accessToken customer along with his/her valid addressId
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    @Modifying
    public AddressEntity deleteAddress(AddressEntity addressEntity, CustomerAddressEntity customerAddressEntity) {
       customerAddressDaoImpl.deleteCustomerAddress(customerAddressEntity);
        return addressDao.deleteAddress(addressEntity);
    }

    //this method returns the state according to the StateUuid
    @Transactional(propagation = Propagation.REQUIRED)
    public StateEntity getStateIdByUuid(String stateUuid) throws AddressNotFoundException {
        return addressDaoImpl.getStateIdByUuid(stateUuid);
    }

    //this is the method logic for returning all the addresses of the accessToken specified in the inout field
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<AddressEntity> getAllAddresses(CustomerEntity customerEntity) {
        return addressDao.getAllAddresses(customerEntity);
    }


    //this returns from the AddressDao the states from it stateId
    @Transactional(propagation = Propagation.REQUIRED)
    public String getStateNameByStateId(long stateId) {
        return addressDao.getStateNameByStateId(stateId);
    }


    //this method returns the addressUuid made
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity searchByUuid(String addressUuid) {
        return addressDao.searchByUuid(addressUuid);
    }


    //this method searches for the addresses using the addressID
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAddressEntity searchByAddressId(long addressId) {
        return customerAddressDaoImpl.searchByAddressId(addressId);
    }


    //this method returns address by addressId
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity getAddressById(Long addressId) {
        return addressDao.getAddressById(addressId);
    }
}
