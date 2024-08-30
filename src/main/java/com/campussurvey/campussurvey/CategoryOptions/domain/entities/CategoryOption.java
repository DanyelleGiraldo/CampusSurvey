package com.campussurvey.campussurvey.CategoryOptions.domain.entities;
import java.time.LocalDateTime;

import com.campussurvey.campussurvey.CategoriesCatalog.domain.entities.CategoriesCatalog;
import com.campussurvey.campussurvey.Option.domain.entities.Option;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="category_options")
public class CategoryOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoryoptions_id")
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="options_id")
    private Option option;

    @ManyToOne
    @JoinColumn(name = "categoirescatalog_id")
    private CategoriesCatalog categoriesCatalog;

    public CategoryOption() {
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

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public CategoriesCatalog getCategoriesCatalog() {
        return categoriesCatalog;
    }

    public void setCategoriesCatalog(CategoriesCatalog categoriesCatalog) {
        this.categoriesCatalog = categoriesCatalog;
    }

    

}
