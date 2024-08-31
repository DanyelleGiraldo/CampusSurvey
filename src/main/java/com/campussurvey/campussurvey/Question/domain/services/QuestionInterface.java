package com.campussurvey.campussurvey.Question.domain.services;

import java.util.List;

import com.campussurvey.campussurvey.Question.domain.entities.Question;

public interface QuestionInterface {
    void save(Question question);
    void delete(Question question);
    void update(Long id, Question question);
    List<Question> findAll();
}
