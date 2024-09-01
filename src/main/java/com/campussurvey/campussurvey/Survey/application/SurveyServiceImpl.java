package com.campussurvey.campussurvey.Survey.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;
import com.campussurvey.campussurvey.Survey.domain.service.SurveyInterface;
import com.campussurvey.campussurvey.Survey.infrastructure.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SurveyServiceImpl implements SurveyInterface {
    @Autowired
    SurveyRepository surveyRepository;

    @Override
    @Transactional
    public void save(Surveys surveys) {
        surveyRepository.save(surveys);
    }

    @Override
    @Transactional
    public void delete(Surveys surveys) {
        surveyRepository.delete(surveys);
    }

    @Override
    @Transactional
    public void update(Long id, Surveys updatedSurvey) {
        Optional<Surveys> existingSurveyOpt = surveyRepository.findById(id);

        if (existingSurveyOpt.isPresent()) {
            Surveys existingSurvey = existingSurveyOpt.get();

            existingSurvey.setCategoriesCatalog(updatedSurvey.getCategoriesCatalog());
            existingSurvey.setDescription(updatedSurvey.getDescription());
            existingSurvey.setName(updatedSurvey.getName());
            existingSurvey.setComponentHtml(updatedSurvey.getComponentHtml());
            existingSurvey.setComponentReact(updatedSurvey.getComponentReact());
            existingSurvey.setUpdatedAt(LocalDateTime.now());

            surveyRepository.save(existingSurvey);
        } else {
            throw new EntityNotFoundException("Survey not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<Surveys> findAll() {
        return surveyRepository.findAll();
    }

    
}
