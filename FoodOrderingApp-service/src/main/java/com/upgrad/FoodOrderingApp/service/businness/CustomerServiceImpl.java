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
    private CustomerDao customerDao;//the dao is Data object model which links from model to controller to view..

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
   {//this method checks whether th input fields are empty or not if they are empty then the returns true where it is called and exceptioin is thrown
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

}