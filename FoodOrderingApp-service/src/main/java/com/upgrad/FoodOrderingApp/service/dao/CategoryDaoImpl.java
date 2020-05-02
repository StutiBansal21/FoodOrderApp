package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    public CategoryEntity getCategoryUsingId(final Long categoryId)
    {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryEntity>list=new ArrayList<>();
        try
        {
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

    public List<CategoryEntity> getAllCategories(){
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<CategoryEntity>list=new ArrayList<>();
        try
        {
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
