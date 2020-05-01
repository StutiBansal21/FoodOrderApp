package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/*
@NamedQueries({
        @NamedQuery( name = "customerItemByCategoryId", query = "select ci from CategoryItemEntity ci where ci.categoryId = :categoryId")
})
*/
@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name = "category_item")//specifies the name of the database table to be used for mapping
public class CategoryItemEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "item_id")
    @NotNull
    private long itemId;

    @Column(name = "category_id")
    @NotNull
    private long categoryId;

    //getter and setter methods of the variables
    public long getId()
    {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {

        this.itemId = itemId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId)
    {
        this.categoryId = categoryId;
    }

    //toString() method for the variables declared in the class
    @Override
    public String toString() {
        return "CategoryItemEntity{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", categoryId=" + categoryId +
                '}';
    }
}
