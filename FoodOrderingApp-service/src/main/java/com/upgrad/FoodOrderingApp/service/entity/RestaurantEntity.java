package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name = "restaurant")//specifies the name of the database table to be used for mapping
public class RestaurantEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")//specifies the name of the column in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "uuid")
    @NotNull
    private String uuid;

    @Column(name = "restaurant_name",nullable = false)
    private String restaurantName;

    @Column(name = "photo_url",nullable = false)
    private String photoUrl;

    @Column(name = "customer_rating")
    @NotNull
    private float customerRating;

    @Column(name = "average_price_for_two",nullable = false)
    private int averagePriceForTwo;

    @Column(name = "number_of_customers_rated",nullable = false)
    private int numberOfCustomersRated;

    @Column(name = "address_id")
    @NotNull
    private long addressId;

    //getter and setter method of the above variables
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

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public float getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(float customerRating) {
        this.customerRating = customerRating;
    }

    public int getAveragePriceForTwo() {
        return averagePriceForTwo;
    }

    public void setAveragePriceForTwo(int averagePriceForTwo) {
        this.averagePriceForTwo = averagePriceForTwo;
    }

    public int getNumberOfCustomersRated() {
        return numberOfCustomersRated;
    }

    public void setNumberOfCustomersRated(int numberOfCustomersRated) {
        this.numberOfCustomersRated = numberOfCustomersRated;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
