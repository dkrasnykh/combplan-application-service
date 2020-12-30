package com.orioninc.combplanapplicationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RequestDto implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty(value = "title")
    private String title;
    @JsonProperty(value = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
