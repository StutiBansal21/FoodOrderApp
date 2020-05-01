package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/*
@NamedQueries({
        @NamedQuery(name = "getAllItems", query = "select i from ItemEntity i")
})
*/
@Entity//specifies that the class is an entity and is mapped to a database table.
@Table(name = "item")//specifies the name of the database table to be used for mapping
public class ItemEntity implements Serializable {

    @Id//specifies the primary key of an entity
    @Column(name = "id")//gives the column name in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)//provides for the specification of generation strategies for the values of primary keys.
    private long id;

    @Column(name = "uuid")
    @NotNull//the value cannot be null type
    private String uuid;

    @Column(name = "item_name")
    @NotNull
    private String ItemName;

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "type")
    @NotNull
    private String type;

    //getter and setter functions
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

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //equals,hashcode,toString methods for the above specified variables
    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
