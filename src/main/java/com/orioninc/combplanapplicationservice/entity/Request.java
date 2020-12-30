package com.orioninc.combplanapplicationservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Request extends EntityBase {
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    public Request() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
