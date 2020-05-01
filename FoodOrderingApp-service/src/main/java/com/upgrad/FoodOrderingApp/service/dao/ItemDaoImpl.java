package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class ItemDaoImpl {

    @PersistenceUnit//defines a set of all entity classes that are managed by EntityManager instances in an application
    private EntityManagerFactory entityManagerFactory;

    //getting the items from the itemId function
    public ItemEntity getItemUsingId(final long itemId)
    {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<ItemEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<ItemEntity> query = entityManager.createQuery("select i from ItemEntity i where i.id = :itemId", ItemEntity.class);
            query.setParameter("itemId", itemId);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();
        if(list.size()==0)
            return null;
        else
            return list.get(0);
    }

}
