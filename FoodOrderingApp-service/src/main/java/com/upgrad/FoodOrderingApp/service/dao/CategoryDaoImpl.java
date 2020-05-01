package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class CategoryDaoImpl {

    @PersistenceUnit//defines a set of all entity classes that are managed by EntityManager instances in an application
    private EntityManagerFactory entityManagerFactory;

    public CategoryEntity getCategoryUsingId(final Long categoryId)
    {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c where c.id = :categoryId", CategoryEntity.class);
            query.setParameter("categoryId", categoryId);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();
        if(list.size() == 0)
            return null;
        else
            return list.get(0);
    }

    public CategoryEntity getCategoryUsingUuid(final String categoryUuid){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<CategoryEntity> query = entityManager.createQuery("select c from CategoryEntity c where c.uuid = :categoryUuid", CategoryEntity.class);
            query.setParameter("categoryUuid", categoryUuid);
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

    //sql mehthod implementation to getAllCategories of items from the database
    public List<CategoryEntity> getAllCategories(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<CategoryEntity> query = entityManager.createQuery("SELECT c FROM CategoryEntity c ORDER BY c.categoryName", CategoryEntity.class);
            list = query.getResultList();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
        entityManager.close();
        /*if(list.size()==0)
            return null;
        else*/
            return list;
    }
}
