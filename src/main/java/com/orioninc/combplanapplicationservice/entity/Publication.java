package com.orioninc.combplanapplicationservice.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;
import java.util.Set;

@Entity
public class Publication extends EntityBase {
    private Long author;
    private String title;
    private String description;

    @CollectionTable(name = "co_authors", joinColumns = @JoinColumn(name = "publication_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"publication_id", "user_id"}, name = "publication_user_unique_idx")})
    @Column(name = "user_id")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "publication_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Long> coAuthors;

    public Publication() {
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
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

    public Set<Long> getCoAuthors() {
        return coAuthors;
    }

    public void setCoAuthors(Set<Long> coAuthors) {
        this.coAuthors = coAuthors;
    }
}
