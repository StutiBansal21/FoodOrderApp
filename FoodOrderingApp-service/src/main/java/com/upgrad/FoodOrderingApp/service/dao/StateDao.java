package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;

public interface StateDao {
    StateEntity getStateById(final Long stateId);

}
