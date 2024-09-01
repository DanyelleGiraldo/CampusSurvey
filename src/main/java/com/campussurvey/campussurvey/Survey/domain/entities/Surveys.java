package com.campussurvey.campussurvey.Survey.domain.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.campussurvey.campussurvey.CategoriesCatalog.domain.entities.CategoriesCatalog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="surveys")
public class Surveys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="survey_id")
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "componenthtml", columnDefinition="VARCHAR(255)")
    private String componentHtml;

    @Column(name = "componentreact", columnDefinition="VARCHAR(255)")
    private String componentReact;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "survey_category", joinColumns = @JoinColumn(name = "survey_id"),
    inverseJoinColumns = @JoinColumn(name = "categoriescatalog_id"))
    private Set<CategoriesCatalog> categoriesCatalog;

    public Surveys() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getComponentHtml() {
        return componentHtml;
    }

    public void setComponentHtml(String componentHtml) {
        this.componentHtml = componentHtml;
    }

    public String getComponentReact() {
        return componentReact;
    }

    public void setComponentReact(String componentReact) {
        this.componentReact = componentReact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CategoriesCatalog> getCategoriesCatalog() {
        return categoriesCatalog;
    }

    public void setCategoriesCatalog(Set<CategoriesCatalog> categoriesCatalog) {
        this.categoriesCatalog = categoriesCatalog;
    }   

}
