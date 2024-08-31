package com.campussurvey.campussurvey.Question.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Question.domain.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{

}
