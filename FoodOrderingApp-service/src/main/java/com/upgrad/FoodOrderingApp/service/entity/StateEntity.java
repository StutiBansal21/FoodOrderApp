package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import java.io.Serializable;


/*@NamedQueries({

        @NamedQuery(name = "getStateByUUID", query = "select s from StateEntity s where s.uuid =:uuid"),
        @NamedQuery(name = "getAllStates", query = "select s from StateEntity s")
})*/
@Entity//specifies that the class is an entity and is mapped to a database table
@Table(name = "state")//specifies the name of the database table to be used for mapping
public class StateEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Column(name = "state_name", nullable = false)
    private String stateName;


    //constructor
    public StateEntity() {
    }

    public StateEntity(long id, String uuid, String stateName) {
        this.id = id;
        this.uuid = uuid;
        this.stateName = stateName;
    }
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
