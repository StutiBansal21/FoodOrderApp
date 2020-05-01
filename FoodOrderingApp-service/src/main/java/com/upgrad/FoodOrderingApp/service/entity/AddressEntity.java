package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/*@NamedQueries({
         @NamedQuery(name = "deleteAddressById", query = "delete from AddressEntity a where a.uuid=:addressuuid"),
        @NamedQuery(name = "getAddressById", query = "select a from AddressEntity a where a.uuid=:addressuuid")

        @NamedQuery(name = "getAllAddress", query = "select b from CustomerAddressEntity a inner join a.address b where " +
                "a.customer = :customer order by b.id desc"),
        @NamedQuery(name = "getAddressByUUID", query = "select b from CustomerAddressEntity a inner join a.address b where " +
                "b.uuid = :uuid")
})*/
@Entity//specifies that the class is an entity and is mapped to a database table
@Table(name = "address")//specifies the name of the database table to be used for mapping
public class AddressEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")//the name of the column in the particular table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "flat_buil_number", nullable = false)
    private String flatBuilNumber;

    @Column(name = "locality",nullable = false)
    private String locality;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "pincode",nullable = false)
    private String pincode;

    @Column(name = "state_id",nullable = false)
    private long stateId;

    //constructor
    public AddressEntity() {}

    public AddressEntity(long id, String uuid, String flatBuilNumber, String locality, String city, String pincode, long stateId) {
        this.id = id;
        this.uuid = uuid;
        this.flatBuilNumber = flatBuilNumber;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.stateId = stateId;
    }




    //getter and setters
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

    public String getFlatBuilNumber() {
        return flatBuilNumber;
    }

    public void setFlatBuilNumber(String flatBuilNumber) {
        this.flatBuilNumber = flatBuilNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }
}
