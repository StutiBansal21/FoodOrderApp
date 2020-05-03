package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name="restaurant_category")//specifies the name of the database table to be used for mapping
public class RestaurantCategoryEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")//specifies the name of the column in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "restaurant_id",nullable = false)
    private long restaurantId;

    @Column(name = "category_id")
    @NotNull
    private long categoryId;

    //getter and setter method implementations
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    //toString() method for the variables declared
    @Override
    public String toString() {
        return "RestaurantCategoryEntity{" +"id=" + id +", restaurantId=" + restaurantId +", categoryId=" + categoryId + '}';
    }
}
