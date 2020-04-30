package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthEntityDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAuthEntityDaoImpl;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDaoImpl;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Pattern;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;//the dao is Data object model which links from model to controller to view..

    @Autowired
    private CustomerDaoImpl customerDaoImpl;

    @Autowired
    private CustomerAuthEntityDaoImpl customerAuthEntityDaoImpl;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider;//for encryption of password we already have in intellij

//signup implementation and validation function
    @Override
    @Transactional(propagation = Propagation.REQUIRED)//means of telling your code when it executes that it must have a Transaction.
    public CustomerEntity saveCustomer(CustomerEntity customerEntity, String firstname, String lastname, String password, String email, String contactNumber) throws SignUpRestrictedException {
        //handle all the validations here if all validations are ok then save in db using Dao
        //if the customer exists in the db using the customer column which is also a primary key
        CustomerEntity object = customerDao.getCustomerByContactNumber(customerEntity.getContactNumber());//gets the value from CustomerDaoImpl
        if (object != null)//this means that there exists in db a customer with the same mobile number so this particular exception thrown
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try other contact number");
        //if (checkIfFieldIsEmpty(customerEntity))
          if(firstname == null || email == null || contactNumber == null ||password == null)//to check whether the fields of all these are empty or not
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        if (!checkEmailPattern(customerEntity))//if the email id is not in the correct format then the exception is thrown
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        if(!checkContactNumber(customerEntity))//if the contact number is not in correct format then this exception is thrown
            throw new SignUpRestrictedException("SGR-003","Invalid contact number!");
        if(!checkPassword(customerEntity))//if password is of wrong format which is explained in the checkPassword method then the particular exception is thrown
            throw new SignUpRestrictedException("SGR-004","Weak password!");
        else {
            //if there is no such exception and all above conditions are satisfactory then encrypt the password for its safety and security purpos
            String[] encryptPassoword = passwordCryptographyProvider.encrypt(customerEntity.getPassword());//this is provided by springboot/hibernate to encrpyt the password
            customerEntity.setSalt(encryptPassoword[0]);//setting the salt
            customerEntity.setPassword(encryptPassoword[1]);//encrypt password
            //salt is an extra layer for better security purpose.an extra layer for defense against hacking
            return customerDao.saveCustomer(customerEntity);//data wiill be saved in db now
            }
    }
   @Override
    public boolean checkIfFieldIsEmpty(final CustomerEntity customerEntity)
   {//this method checks whether the input fields are empty or not if they are empty then the returns true where it is called and exception is thrown
        if (customerEntity.getFirstName().length() == 0 || customerEntity.getLastName().length() == 0 || customerEntity.getContactNumber().length() == 0 || customerEntity.getEmailAddress().length() == 0 || customerEntity.getPassword().length() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkEmailPattern(final CustomerEntity customerEntity) {
        //this method checks that the email pattern is correct according to the constraints specified or not
       /* String emailExpression = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailExpression);
        if (pattern.matcher(emailExpression).matches())
            return true;
        else
            return false;*/
        final String email = customerEntity.getEmailAddress();
        return email.contains("@") && email.contains(".") && !email.contains(" ");//it checks whether the email string has @,. and it doesnt have space in it
    }

    @Override
    public boolean checkContactNumber(CustomerEntity customerEntity) {
        //constraint specified is that the contactNumber should be of digits only and it should be of length 10 only and if the constraints are not followed it should throw an exception
        String expression = "^[0-9]{10}$";//to check whether the contact number is of 0-9 numbers only and size is 10
        String contact = customerEntity.getContactNumber();
        if( contact.matches(expression)){
            return true;
        }
        else
            return false;
        //int number = Integer.parseInt(contact);
        //if length of number is less than 10 then return true
        /*try {
            if (contact.length() != 10)
                return false;
            else
                return true;
        } catch (NumberFormatException e) {
            return false;
        }*/
    }

    @Override
    public boolean checkPassword(CustomerEntity customerEntity) {
        //the password constraints specified are tht shouldn't be less than 8 characters of length
        //it should have atleast 1 uppercase and lowercase alphabet each, shuld have atleast 1 number adn should have atleast 1 special character.
        String pass=customerEntity.getPassword();
        if(pass.length()<8||!pass.matches("(?=.*[0-9]).*")||!pass.matches("(?=.*[A-Z]).*")||!pass.matches("(?=.*[~!@#$%^&*()_-]).*"))
            return false;
            else
        return true;
    }

    //-----> login logic implementation
    @Override
    @Transactional(propagation =Propagation.REQUIRED)
    public CustomerAuthEntity verifyAuthenticate(String contactNumber, String password) throws AuthenticationFailedException {
        //boolean checkData=false;
        CustomerEntity customerEntity=customerDaoImpl.getCustomerByContactNumber(contactNumber);//Using customerDaoImpl to find the user based on contact number
        if(customerEntity==null)// if contact number provided by customer is not exist in database
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        else{
            //verifyauthenticate the user and encrypt the password received from the Customer with the salt added as well
        final String encryptedPassword = PasswordCryptographyProvider.encrypt(password, customerEntity.getSalt());//for pssword encryption checker

        if (encryptedPassword.equals(customerEntity.getPassword()))
        {
            //customer has been authenticated and verified .Now create a JWT token
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();//store data and token in db using CustomerAuthEntity

            /*customerAuthEntity = customerDaoImpl.getCustomerAuthEntityTokenByUUID(customerEntity.getUuid());
            if (customerAuthEntity != null) {
                checkData = true;
            } else {
                customerAuthEntity = new CustomerAuthEntity();
            }*/

            //setting values in the customer_auth table
            customerAuthEntity.setUuid(UUID.randomUUID().toString());//set the uuid randomly generated in customer_ath table
            customerAuthEntity.setCustomerId(customerEntity.getId());//set the customerid which is the primary key of customer table "id"
            final ZonedDateTime now = ZonedDateTime.now();//finding the current login time of customer
            final ZonedDateTime expiresAt = now.plusHours(8);//automatic logout time or the time the customer/user can remain logged in
            customerAuthEntity.setLoginAt(now);//login time is set in the column at the time of login of customer
            customerAuthEntity.setExpiresAt(expiresAt);//setting the max login or the expirytime in db

            //customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
            String accessToken = jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt);
            customerAuthEntity.setAccessToken(accessToken);//setting the accessToken in the customer_Auth table
            customerAuthEntity.setLogoutAt(null);

            // if already uuid so new token generate for existing previous will merge else another user with new token
         /*   if (checkData)
                customerDaoImpl.createAuthToken(customerAuthEntity);
            else
                customerDaoImpl.updateLoginInfo(customerAuthEntity);*/

            customerAuthEntityDaoImpl.create(customerAuthEntity);// Persist the CustomerAuthEntity generated, in the database
            customerDaoImpl.updateUser(customerEntity);//updating the user/customer value in customer,customer_auth table
            return customerAuthEntity;// Return the CustomerAuthEntity generated

            /*customerAuthEntity.setLoginAt(ZonedDateTime.now());
            customerAuthEntity.setExpiresAt(expiresAt);
            customerAuthEntity.setAccessToken(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
            return customerAuthEntity;*/

        } else {
            // if the password does not match with the existing password present in database
            throw new AuthenticationFailedException("ATH-002", "Invalid Credentials");
        }
    }}

    @Transactional
    public CustomerEntity getCustomerByContactNumber(final String contactNumber) {
        //runs the method according to the sql queries and returns the value/result of the query
        return customerDaoImpl.getCustomerByContactNumber(contactNumber);
    }

    @Transactional
    public CustomerEntity searchByUuid(final String uuid) {
        //runs the method according to the sql queries and returns the value/result of the query
        return customerDaoImpl.searchByUuid(uuid);
    }

    @Transactional
    public CustomerEntity searchById(final long id) {
        //runs the method according to the sql queries and returns the value/result of the query
        return customerDaoImpl.searchById(id);
    }


    /*//function called in customer controller to check valid format and it returns boolean value
    public static boolean validAuthFormat(String authorization)
    {
        String regexStr = "^[0-9]{10}$";
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        String basic = authorization.split("Basic")[1];
        if(contactNumberVerified(decodedArray[0]) && passwordVerified(decodedArray[1]) ){
            return  true;}
        else {return false;}
     }

    // function called in validAuthFormat to verify contact number or ATH-003
    public static boolean contactNumberVerified(String cno)
    {
        String str= "^[0-9]{10}$";
        if(cno.matches(str))
            return true;
        else
            return false;
    }
    // function called in validAuthFormat to verifyPassword
    public static boolean passwordVerified(String password)
    {
        if(password.length()<8 || !password.matches("(?=.*[0-9]).*")|| !password.matches("(?=.*[A-Z]).*")|| !password.matches("(?=.*[~!@#$%^&*()_-]).*"))
          return false;

        return true;
    }
    //-------------->Logout
    //checks whether customer has loggedout based on access token
    /*@Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(final String access_token) throws AuthorizationFailedException {
        CustomerAuthEntity customerAuthEntity = customerDaoImpl.getAuthTokenByAccessToken(access_token);
        if (customerAuthEntity == null) {
             //if access token provided by the customer not exist in database
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        else if(customerAuthEntity!=null&&customerAuthEntity.getLoginAt()!=null){
            //if (customerAuthEntity.getLogoutAt() != null) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }
       else if (customerAuthEntity!=null && ZonedDateTime.now().isAfter(customerAuthEntity.getExpiresAt())) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }
        else
        {
            final ZonedDateTime now=ZonedDateTime.now();
            customerAuthEntity.setLogoutAt(now);
            return customerAuthEntity;
        }
    }*/


    //logout implementation and validation
   @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity logout(final String accessToken)throws AuthorizationFailedException
    {
        //returns the customer with that particular accessToken from the db
        CustomerAuthEntity customerAuthEntity = customerAuthEntityDaoImpl.getAuthTokenByAccessToken(accessToken);
        if(customerAuthEntity == null)
        {
            //if access token provided by the customer not exist in database
            throw new AuthorizationFailedException("ATHR-001","Customer is not logged in.");
        }
        if(!isUserLoggedIn(accessToken)) //if the method returns false ie. there is no customer in the db  with the accessToken anymore
        {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        if(checkExpiryOfToken(accessToken))//if the expiry time or the max login time has exceeded even though the customer was loggedin he/she gets automatically loggedout
        {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        ZonedDateTime now = ZonedDateTime.now();//getting the current time
        long difference = customerAuthEntity.getExpiresAt().compareTo(now);//finding the difference of maxlogin time and current time
        if(customerAuthEntity.getLogoutAt() != null)//if the logout is not null
        {
            throw new AuthorizationFailedException("AUTH-002", "Customer is logged out. Log in again to access this endpoint.");
        }
        else if (difference < 0)
        {
            //if the difference is in negative ie. current time-maxLogin or expiry that means the login session has expired and customer is automatically logged out
            throw new AuthorizationFailedException("AUTH-003", "Your session is expired. Log in again to access this endpoint.");
        }
        else
            {
            customerAuthEntity.setLogoutAt(now);//changiing the logout time
            customerAuthEntityDaoImpl.updateCustomer(customerAuthEntity);//changing the values in table
            }
        return customerAuthEntity;
    }
    // this function is used to verify the token of customer
    @Transactional
    public boolean checkExpiryOfToken(final String accessToken) {
       CustomerAuthEntity customerAuthEntity = customerAuthEntityDaoImpl.getAuthTokenByAccessToken(accessToken);
        if(customerAuthEntity == null) {
            return true;
        }
        ZonedDateTime now = ZonedDateTime.now();
        long diff = customerAuthEntity.getExpiresAt().compareTo(now);
        if(diff < 0)
            return true;
        else
            return false;
    }

    @Transactional
    public boolean isUserLoggedIn(final String accessToken) {
        //checking of the customer is still logged in or not
       CustomerAuthEntity customerAuthEntity = customerAuthEntityDaoImpl.getAuthTokenByAccessToken(accessToken);
        if(customerAuthEntity == null)
            return false;

        ZonedDateTime logoutAt = customerAuthEntity.getLogoutAt();
        ZonedDateTime loginAt = customerAuthEntity.getLoginAt();
        if(logoutAt == null)
            return true;

        long diff = loginAt.compareTo(logoutAt);
        if(diff > 0)
            return true;
        else
            return false;
    }

    @Transactional
    public CustomerEntity getCustomer (final String accessToken) throws AuthorizationFailedException {
          final CustomerAuthEntity customerAuthEntity = customerAuthEntityDaoImpl.getAuthTokenByAccessToken(accessToken);
        if(customerAuthEntity == null) {
            //if the access token is null ie the customerAuthEntity is null means there is no customer logged in of that particular accessToken
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }
        ZonedDateTime now = ZonedDateTime.now();
        long difference = customerAuthEntity.getExpiresAt().compareTo(now);
        CustomerEntity customerEntity = customerDaoImpl.searchById(customerAuthEntity.getCustomerId());
        return customerEntity;
    }


    //password updatation method implementation
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomerPassword(final String oldPassword,final String newPassword,final CustomerEntity customerEntity)throws UpdateCustomerException {
        /*CustomerAuthEntity customerAuthEntity=customerDaoImpl.getAuthTokenByAccessToken(accessToken);//rturns the daoimpl function on the query
        CustomerEntity customerEntity=customerAuthEntity.getCustomer();//returns the customer
        final String decryptedPassword = passwordCryptographyProvider.encrypt(oldPassword,customerAuthEntity.getCustomer().getSalt());*///to encrypt the password and return the old pasword along with the salt
        CustomerEntity customerAuthEntity1=new CustomerEntity();
        final String oldPasswordString=passwordCryptographyProvider.encrypt(oldPassword, customerEntity.getSalt());
        final String newPasswordString=passwordCryptographyProvider.encrypt(oldPassword, customerEntity.getSalt());
        if( oldPassword.length()==0 || newPassword.length()==0 )//if password whether old or new is empty then this exception is thrown
        {
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        }
        customerAuthEntity1.setPassword(newPassword);//set the new password
   /*     if(customerAuthEntity==null)
        {
            //if no customer is in the login state
            throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in.");
        }
        else if(customerAuthEntity!=null&&customerAuthEntity.getLogoutAt()!=null){
            //if the customer has already logged out before
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out. Log in again to access this endpoint.");
        }
        else if(customerAuthEntity!=null && ZonedDateTime.now().isAfter(customerAuthEntity.getExpiresAt())){
            //if his access token time has expired or the time for the person to remain loggedin has exceeded
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access this endpoint.");
        }*/
        if(!checkPassword(customerAuthEntity1)){
            //if the new password doesnt match the constraints
            throw new UpdateCustomerException("UCR-001","Weak password!");
        }
        if(!customerEntity.getPassword().matches(oldPasswordString))
        {
            //if the old password stored in db doesnt match with the password u input then this exception is thrown
            throw new UpdateCustomerException("UCR-004","Incorrect old password!");
        }
        customerEntity.setPassword(newPasswordString);//set the password as new password
        customerDaoImpl.updateUser(customerEntity);//make changes into the db
        return customerEntity;
       /* else
        {
            //if above all validations constraints are verified and there is no issue then we encrypt the new password along with the salt
            String encrpted[]=passwordCryptographyProvider.encrypt(newPassword);
            customerEntity.setSalt(encrpted[0]);
            customerEntity.setPassword(encrpted[1]);
            customerDaoImpl.updateCustomer(customerEntity);
            return customerAuthEntity.getCustomer();}*/
        }
}