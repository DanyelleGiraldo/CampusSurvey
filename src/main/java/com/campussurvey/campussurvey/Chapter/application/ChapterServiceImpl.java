package com.campussurvey.campussurvey.Chapter.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;
import com.campussurvey.campussurvey.Chapter.domain.service.ChapterInterface;
import com.campussurvey.campussurvey.Chapter.infrastructure.ChapterRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ChapterServiceImpl implements ChapterInterface {

    @Autowired
    ChapterRepository chapterRepository;

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
        Optional<Chapter> existingChapterOpt = chapterRepository.findById(id);

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

}
