package com.campussurvey.campussurvey.Chapter.application;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;
import com.campussurvey.campussurvey.Chapter.domain.service.ChapterInterface;
import com.campussurvey.campussurvey.Chapter.infrastructure.ChapterRepository;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;
import com.campussurvey.campussurvey.Survey.infrastructure.SurveyRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ChapterServiceImpl implements ChapterInterface {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Override
    @Transactional
    public void save(Chapter chapter) {
        chapterRepository.save(chapter);
    }

    @Override
    @Transactional
    public void delete(Chapter chapter) {
        chapterRepository.delete(chapter);
    }

    @Override
    @Transactional
    public void update(Long id, Chapter updatedChapter) {
        Optional<Chapter> existingChapterOpt = chapterRepository.findById(updatedChapter.getId());

        if (existingChapterOpt.isPresent()) {
            Chapter existingChapter = existingChapterOpt.get();
            
            existingChapter.setChapterNumber(updatedChapter.getChapterNumber());
            existingChapter.setChapterTitle(updatedChapter.getChapterTitle());
            existingChapter.setComponentHtml(updatedChapter.getComponentHtml());
            existingChapter.setComponentReact(updatedChapter.getComponentReact());
            existingChapter.setUpdatedAt(LocalDateTime.now());
            existingChapter.setQuestions(updatedChapter.getQuestions());
            existingChapter.setSurveys(updatedChapter.getSurveys());
            
            chapterRepository.save(existingChapter);
        } else {
            throw new EntityNotFoundException("Chapter not found with id: " + id);
        }
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    @Transactional
    public Surveys assingChaptersToSurvey(Long surveyId, List<Long> chapterIds) {
        Optional<Surveys> optionalSurvey = surveyRepository.findById(surveyId);
        if (!optionalSurvey.isPresent()) {
            throw new RuntimeException("Survey not found with id: " + surveyId);
        }

        Surveys survey = optionalSurvey.get();

        List<Chapter> chapterList = chapterRepository.findAllById(chapterIds);
        if (chapterList.isEmpty()) {
            throw new RuntimeException("No chapters found with the provided ids.");
        }

        Set<Chapter> chapterSet = new HashSet<>(chapterList);
        for (Chapter chapter : chapterSet) {
            chapter.setSurveys(survey);  
        }

        chapterRepository.saveAll(chapterSet);

        return survey;
    }


}
