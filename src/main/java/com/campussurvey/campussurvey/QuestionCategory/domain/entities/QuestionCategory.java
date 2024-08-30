package com.campussurvey.campussurvey.QuestionCategory.domain.entities;

import java.time.LocalDateTime;

import com.campussurvey.campussurvey.CategoryOptions.domain.entities.CategoryOption;
import com.campussurvey.campussurvey.Question.domain.entities.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="question_categories")
public class QuestionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="questioncategories_id")
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name="categoryoptions_id")
    private CategoryOption categoryOption;

    public QuestionCategory() {
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public CategoryOption getCategoryOption() {
        return categoryOption;
    }

    public void setCategoryOption(CategoryOption categoryOption) {
        this.categoryOption = categoryOption;
    }

    

}
