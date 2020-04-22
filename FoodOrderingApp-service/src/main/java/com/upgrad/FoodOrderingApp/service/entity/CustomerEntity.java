package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="CUSTOMER")
//named queries add krna hai
public class CustomerEntity implements Serializable {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(name="uuid",nullable = false)
private String uuid;
@Column(name = "firstName",nullable = false)
private String firstName;

@Column(name = "lastName",nullable = false)
private String lastName;

@Column(name = "email",nullable = false)
private String emailAddress;

@Column(name = "password",nullable = false)
private String password;

@Column(name = "contact_Number",nullable = false)
private String contactNumber;

@Column(name = "salt",nullable = false)
private String salt;

//constructor
public CustomerEntity(){

}
//getter setter of all the variables
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
