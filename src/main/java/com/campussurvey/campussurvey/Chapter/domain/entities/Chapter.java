package com.campussurvey.campussurvey.Chapter.domain.entities;

import java.time.LocalDateTime;
import java.util.Set;

import com.campussurvey.campussurvey.Question.domain.entities.Question;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "chapters_id")
    private Long id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "componenthtml", columnDefinition="VARCHAR(20)")
    private String componentHtml;

    @Column(name = "componentreact", columnDefinition="VARCHAR(20)")
    private String componentReact;

    @Column(name = "chapter_number", columnDefinition="VARCHAR(50)")
    private String chapterNumber;

    @Column(name = "chapter_title", columnDefinition = "VARCHAR(50)")
    private String chapterTitle;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Surveys surveys;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "question_chapters", joinColumns = @JoinColumn(name = "chapters_id"),
    inverseJoinColumns = @JoinColumn(name = "question_id"))
    private Set<Question> questions;

    public Chapter() {
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

    public String getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(String chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public Surveys getSurveys() {
        return surveys;
    }

    public void setSurveys(Surveys surveys) {
        this.surveys = surveys;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

}
