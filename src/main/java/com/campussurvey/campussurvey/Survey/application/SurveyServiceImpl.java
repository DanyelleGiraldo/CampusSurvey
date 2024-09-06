package com.campussurvey.campussurvey.Survey.application;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.CategoriesCatalog.domain.entities.CategoriesCatalog;
import com.campussurvey.campussurvey.CategoriesCatalog.infrastructure.CategoriesCatalogRepository;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;
import com.campussurvey.campussurvey.Survey.domain.service.SurveyInterface;
import com.campussurvey.campussurvey.Survey.infrastructure.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SurveyServiceImpl implements SurveyInterface {
    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    private CategoriesCatalogRepository categoriesCatalogRepository;

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

    @Override
    public Optional<Surveys> findById(Long id) {
        return surveyRepository.findById(id);
    }

    
    @Override
    @Transactional
    public Surveys assignCategoriesToSurvey(Long surveyId,  List<Long> categoryIds) {
        Optional<Surveys> optionalSurvey = surveyRepository.findById(surveyId);
        if (!optionalSurvey.isPresent()) {
            throw new RuntimeException("Survey not found");
        }

        Surveys survey = optionalSurvey.get();

        List<CategoriesCatalog> categoryList = categoriesCatalogRepository.findAllById(categoryIds);
        Set<CategoriesCatalog> categorySet = new HashSet<>(categoryList);
        survey.setCategoriesCatalog(categorySet);

        return surveyRepository.save(survey);

    }

    

    
    
}
