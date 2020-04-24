package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity saveCustomer(CustomerEntity customerEntity) {
        //handle the validations
//signup validations here

//		if(customerDao.getCustomer(customerEntity.getContactNumber() != null) {
//			//throw exception
//		}


        //if validations are okay then save the customer in db;




        String[] encryptPassoword = passwordCryptographyProvider.encrypt(customerEntity.getPassword());

        customerEntity.setSalt(encryptPassoword[0]);
        customerEntity.setPassword(encryptPassoword[1]);

        return customerDao.saveCustomer(customerEntity);

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

            //	return customerDao.createCustomerAuth(customerAuthEntity);
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

    public CustomerAuthEntity verifyAuthenticate(final String cno, final String pswd)throws AuthenticationFailedException
    {
        CustomerDaoImpl obj= new CustomerDaoImpl();
      CustomerEntity centity = obj.getCustomerByCno(cno);
      if(centity== null)
      {
throw new AuthenticationFailedException("ATH-001","This contact number has not been registered!");
      }

      final String encPswd = passwordCryptographyProvider.encrypt(pswd,centity.getSalt());
      if(encPswd.equals(centity.getPassword()))
      {
        JwtTokenProvider jtp = new JwtTokenProvider(encPswd);
        CustomerAuthEntity cat = new CustomerAuthEntity();
        cat.setCustomer(centity);
        final ZonedDateTime now =ZonedDateTime.now();
        final ZonedDateTime expAt = now.plusHours(8);
        cat.setAccessToken(jtp.generateToken(centity.getUuid(),now,expAt));
        cat.setLoginAt(now);
        cat.setExpiresAt(expAt);
        cat.setUuid(centity.getUuid());
        obj.createAuthToken(cat);
        obj.updateCustomer(centity);
        return cat;
      }
      else
      {
       throw new AuthenticationFailedException("ATH-002","Invalid Credentials");
      }
    }

    public static boolean validAuthFormat(String auth)
    {
        String regexStr ="^[0-9]{10}$";
        byte[] dc= Base64.getDecoder().decode(auth.split("Basic")[1]);
        String dt= new String(dc);
        String[] decodedarr= dt.split(":");

        String bas= auth.split("Basic")[1];
        if(contactNumberVerified(decodedarr[0]) && passwordVerified(decodedarr[1]))
      return true;
        else
            return false;
    }

    public static boolean contactNumberVerified(String cno)
    {
        String regexStr= "^[0-9]{10}$";
        if(cno.matches(regexStr))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean passwordVerified(String pswd)
    {
     if(pswd.length()<8 || !pswd.matches("(?=.*[0-9]).*"))
        {
            return false;
        }
     return true;
    }


    @Override
    public CustomerEntity getCustomer(String access_token) throws AuthorizationFailedException {

        authorization(access_token);
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthByAccesstoken(access_token);
        return customerAuthEntity.getCustomer();
    }
}
