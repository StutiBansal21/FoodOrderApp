package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name="restaurant_item")//specifies the name of the database table to be used for mapping
public class RestaurantItemEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")//specifies the name of the column in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "restaurant_id")
    @NotNull
    private int restaurantId;

    //getter and setter functions
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
