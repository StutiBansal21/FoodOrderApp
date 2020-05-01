package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class CategoryItemDaoImpl {

    @PersistenceUnit//defines a set of all entity classes that are managed by EntityManager instances in an application
    private EntityManagerFactory entityManagerFactory;

    public List<CategoryItemEntity> getItemsUsingCategoryId(final long categoryId)
    {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryItemEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<CategoryItemEntity> query = entityManager.createQuery("SELECT c FROM CategoryItemEntity c WHERE c.categoryId = :categoryId", CategoryItemEntity.class);
            query.setParameter("categoryId", categoryId);
            list = query.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        entityManager.close();
       /* if(list.size()==0)
            return null;
        else*/
            return list;
    }
}
