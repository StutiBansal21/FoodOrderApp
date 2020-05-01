package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;


/*@NamedQueries({
       // @NamedQuery(name = "searchByUuid", query = "select c from CustomerAuthEntity c where c.uuid = :uuid"),
        @NamedQuery(name = "customerAuthByAccesstoken", query = "select c from CustomerAuthEntity c where c.accessToken = :accessToken")
})*/
@Entity
@Table(name = "customer_auth")//specifies the name of the database table to be used for mapping
public class CustomerAuthEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UUID", nullable = false)
    private String uuid;

 /*   @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;*/

    @Column(name = "customer_id")
    @NotNull
    private long customerId;

    @Column(name = "access_token",length = 500, nullable = false)
    private String accessToken;

    @Column(name = "EXPIRES_AT", nullable = false)
    private ZonedDateTime expiresAt;

    @Column(name = "login_at", nullable = false)
    private ZonedDateTime loginAt;

    @Column(name = "logout_at")
    private ZonedDateTime logoutAt;

/*  public CustomerAuthEntity() {}
*/

  /*  public CustomerAuthEntity(String uuid, CustomerEntity customer, String accessToken,ZonedDateTime expiresAt,ZonedDateTime loginAt, ZonedDateTime logoutAt) {
        this.uuid = uuid;
        this.customer = customer;
        this.accessToken = accessToken;
        this.expiresAt = expiresAt;
        this.loginAt = loginAt;
        this.logoutAt = logoutAt;
    }*/

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

   /* public CustomerEntity getCustomer() {
        return customer;
    }


    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
*/

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ZonedDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public ZonedDateTime getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(ZonedDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public ZonedDateTime getLogoutAt() {
        return logoutAt;
    }

    public void setLogoutAt(ZonedDateTime logoutAt) {
        this.logoutAt = logoutAt;
    }
}
