package com.campussurvey.campussurvey.Survey.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;


public interface SurveyRepository extends JpaRepository<Surveys, Long> {

}
