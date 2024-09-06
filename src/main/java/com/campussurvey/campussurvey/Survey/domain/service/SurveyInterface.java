package com.campussurvey.campussurvey.Survey.domain.service;

import java.util.List;
import java.util.Optional;

import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

public interface SurveyInterface {
    void save(Surveys surveys);
    void delete(Surveys surveys);
    void update(Long id, Surveys updatedSurvey);
    List<Surveys>  findAll();
    Surveys assignCategoriesToSurvey(Long surveyId, List<Long> categoryIds);
    Optional<Surveys> findById(Long id);

}
