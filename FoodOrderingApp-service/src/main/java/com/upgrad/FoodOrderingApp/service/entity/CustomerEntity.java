package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*@Entity//specifies that the class is an entity and is mapped to a database table
@Table(name="CUSTOMER")//tells us in which table in the database we have to go
@NamedQueries(
        {
              //statically defined query with a predefined unchangeable query string.
                @NamedQuery(name = "customerByContactNumber", query = "select u from CustomerEntity u where u.contactNumber =:contactNumber"),
                @NamedQuery(name = "searchById",query = "Select c from CustomerEntity c where c.id = :id"),
                @NamedQuery(name = "UserQueryByPassword",query = "select c from CustomerEntity c where c.contactNumber=:contactNumber and c.password=:password")
        }
)*/
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity implements Serializable {

@Id//member field below is the primary key of current entity.
@Column(name = "id")//the name of the column of the table
@GeneratedValue(strategy = GenerationType.IDENTITY)//configure the way of increment of the specified column(field)/how the primary key should be generated
private long id;

@Column(name="uuid",nullable = false)
private String uuid;

@Column(name = "firstname",nullable = false)
private String firstName;

@Column(name = "lastname",nullable = false)
private String lastName;

@Column(name = "email",nullable = false)
private String emailAddress;

@Column(name = "password",nullable = false)
private String password;

@Column(name = "contact_number",nullable = false)
private String contactNumber;

@Column(name = "salt",nullable = false)
private String salt;

//constructor
public CustomerEntity(){

}
//getter setter of all the variables
    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AddressEntity> address = new ArrayList<AddressEntity>();

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }
}
