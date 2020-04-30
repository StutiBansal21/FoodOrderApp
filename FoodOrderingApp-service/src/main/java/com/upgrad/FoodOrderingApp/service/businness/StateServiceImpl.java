package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service//classes that provide some business functionality.It is to mark the class as a service provider
public class StateServiceImpl implements StateService{

    @Autowired
    private StateDao stateDao;

//return the stateId according to the stateId passed
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StateEntity getStateById(Long stateId)
    {
        return stateDao.getStateById(stateId);
    }
}
