package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository//indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public class StateDaoImpl implements StateDao{

    @PersistenceUnit//set of entities such that for any persistent identity there is a unique entity instance.
    private EntityManagerFactory entityManagerFactory;

    @Override
    public StateEntity getStateById(Long stateId) {
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        List<StateEntity>list=new ArrayList<>();
        try
        {
            //the query is written which searches for the required in the database
            TypedQuery<StateEntity>query=entityManager.createQuery("select u from StateEntity u where u.id=:stateId",StateEntity.class);
            query.setParameter("stateId",stateId);
            list=query.getResultList();
        }
        catch (Exception exception)
        {
            return null;
        }
        entityManager.close();
        if(list.size()==0)
            return null;
        else
            return list.get(0);
    }
}
