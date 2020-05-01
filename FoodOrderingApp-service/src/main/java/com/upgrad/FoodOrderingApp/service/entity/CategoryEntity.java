package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name = "category")//specifies the name of the database table to be used for mapping
public class CategoryEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "uuid")
    @NotNull
    private String uuid;

    @Column(name = "category_name")
    private String categoryName;

    //getter and setter methods of the vriables
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
