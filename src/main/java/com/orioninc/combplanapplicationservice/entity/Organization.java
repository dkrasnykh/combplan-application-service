package com.orioninc.combplanapplicationservice.entity;

import javax.persistence.Entity;

@Entity
public class Organization extends EntityBase {
    private String name;

    public Organization() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
