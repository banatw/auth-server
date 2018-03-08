package com.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AppClient {
    @Id
    private Integer clientId;
    private String name;
    private String redirectUrl;


    public AppClient() {
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
