package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;//for encryption of password we already have in intellij

//signup implementation and validation
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity, String firstname, String lastname, String password, String email, String contactNumber) throws SignUpRestrictedException {
        //handle all the validations here if all validations are ok then save in db using Dao
        //if the customer exists in the db using the customer column which is also a primary key
        CustomerEntity object = customerDao.getCustomerByContactNumber(customerEntity.getContactNumber());
        if (object != null)
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number");
        //if (checkIfFieldIsEmpty(customerEntity))
          if(firstname == null || email == null || contactNumber == null ||password == null)
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        if (!checkEmailPattern(customerEntity))
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        if(!checkContactNumber(customerEntity))
            throw new SignUpRestrictedException("SGR-003","Invalid contact number!");
        if(!checkPassword(customerEntity))
            throw new SignUpRestrictedException("SGR-004","Weak password!");
        else {
            String[] encryptPassoword = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
            customerEntity.setSalt(encryptPassoword[0]);//setting the salt
            customerEntity.setPassword(encryptPassoword[1]);//encrypt password
            //salt is an extra layer for better security purpose.an extra layer for defense against hacking
            return customerDao.saveCustomer(customerEntity);//uske baad dis will be called and the daata b saved in db
            }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity login(String contactNumber, String password) throws AuthenticationFailedException {

        CustomerEntity customerEntity = null;
        //	CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(contactNumber);

        if (customerEntity == null) {
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }

        final String encryptedPassword = PasswordCryptographyProvider.encrypt(password, customerEntity.getSalt());

        if (encryptedPassword.equals(customerEntity.getPassword())) {
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setUuid(UUID.randomUUID().toString());
            customerAuthEntity.setCustomer(customerEntity);
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime expiresAt = now.plusHours(8);

            customerAuthEntity.setLoginAt(ZonedDateTime.now());
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));

            // return customerDao.createCustomerAuth(customerAuthEntity);
            return null;
        } else {
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
    }

    public void authorization(String access_token) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);

        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }

        if (customerAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        if (ZonedDateTime.now().isAfter(customerAuthEntity.getExpiresAt())) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
    }

    @Override
    public CustomerEntity getCustomer(String access_token) throws AuthorizationFailedException {

        authorization(access_token);
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);
        return customerAuthEntity.getCustomer();
    }

   @Override
    public boolean checkIfFieldIsEmpty(final CustomerEntity customerEntity) {
        if (customerEntity.getFirstName().length() == 0 || customerEntity.getLastName().length() == 0 || customerEntity.getContactNumber().length() == 0 || customerEntity.getEmailAddress().length() == 0 || customerEntity.getPassword().length() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkEmailPattern(final CustomerEntity customerEntity) {
       /* String emailExpression = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailExpression);
        if (pattern.matcher(emailExpression).matches())
            return true;
        else
            return false;*/
        final String email = customerEntity.getEmailAddress();
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    @Override
    public boolean checkContactNumber(CustomerEntity customerEntity) {
        String contact = customerEntity.getContactNumber();
        int number = Integer.parseInt(contact);
        //if length of number is less than 10 then return true
        try {
            if (contact.length() != 10)
                return false;
            else
                return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean checkPassword(CustomerEntity customerEntity) {
        String pass=customerEntity.getPassword();
        if(pass.length()<8||!pass.matches("(?=.*[0-9]).*")||!pass.matches("(?=.*[A-Z]).*")||!pass.matches("(?=.*[~!@#$%^&*()_-]).*"))
            return false;
            else
        return true;
    }

    @Override
    public boolean checkAuthenticationFormat(String authorization) {
        return false;
    }
}