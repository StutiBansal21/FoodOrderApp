package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/*
@NamedQueries({
        @NamedQuery( name = "customerItemByCategoryId", query = "select ci from CategoryItemEntity ci where ci.categoryId = :categoryId")
})
*/
@Entity
@Table(name = "category_item")
public class CategoryItemEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_id")
    @NotNull
    private long itemId;

    @Column(name = "category_id")
    @NotNull
    private long categoryId;

    public long getId() {
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

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "CategoryItemEntity{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", categoryId=" + categoryId +
                '}';
    }
}
