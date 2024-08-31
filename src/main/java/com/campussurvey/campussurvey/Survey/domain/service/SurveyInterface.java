package com.campussurvey.campussurvey.Survey.domain.service;

import java.util.List;

import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

public interface SurveyInterface {
    void save(Surveys surveys);
    void delete(Surveys surveys);
    void update(Long id, Surveys updatedSurvey);
    List<Surveys>  findAll();
    
}
