package com.upgrad.FoodOrderingApp.service.entity;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



/*@NamedQuery(name = "userByAddress", query = "select a from CustomerAddressEntity a inner join a.address b where "
        +"b.uuid = :uuid")*/
@Entity
@Table(name = "customer_address")//specifies the name of the database table to be used for mapping
public class CustomerAddressEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id",nullable = false)
    private long customerId;

    @Column(name = "address_id",nullable = false)
    private long addressId;

    //getter and setter methods of the variables
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
