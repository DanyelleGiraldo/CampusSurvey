package com.campussurvey.campussurvey.Question.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Question.domain.entities.Question;
import com.campussurvey.campussurvey.Question.domain.services.QuestionInterface;
import com.campussurvey.campussurvey.Question.infrastructure.QuestionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuestionServiceImp implements QuestionInterface {
    @Autowired
    QuestionRepository questionRepository;

    @Override
    @Transactional
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    @Transactional
    public void update(Long id, Question question) {
        Optional<Question> existingQuestion = questionRepository.findById(question.getId());

        if (existingQuestion.isPresent()){
            Question existQuestion = existingQuestion.get();

            existQuestion.setQuestionNumber(question.getQuestionNumber());
            existQuestion.setResponseType(question.getResponseType());
            existQuestion.setCommentQuestion(question.getCommentQuestion());
            existQuestion.setQuestionText(question.getQuestionText());
            existQuestion.setChapters(question.getChapters());
            existQuestion.setUpdatedAt(LocalDateTime.now());
            questionRepository.save(question);

        } else {
        throw new EntityNotFoundException("Survey not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<Question> findAll() {
        return questionRepository.findAll();
    }
    
}
