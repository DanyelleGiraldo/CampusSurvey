package com.campussurvey.campussurvey.Chapter.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.campussurvey.campussurvey.Chapter.application.ChapterServiceImpl;
import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;
import com.campussurvey.campussurvey.Survey.application.SurveyServiceImpl;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {
    @Autowired
    private ChapterServiceImpl chapterServiceImpl;

    @Autowired
    private SurveyServiceImpl surveyServiceImpl;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping
    public List<Chapter> listAllChapters(){
        return chapterServiceImpl.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createChapter(@RequestParam Long surveyId, @Valid @RequestBody Chapter chapter, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            Optional<Surveys> optionalSurvey = surveyServiceImpl.findById(surveyId); 
            if (optionalSurvey.isPresent()) {
                Surveys survey = optionalSurvey.get(); 
                chapter.setSurveys(survey);  
                chapterServiceImpl.save(chapter);
                return ResponseEntity.status(HttpStatus.CREATED).body(chapter);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + surveyId);
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable Long id, @Valid @RequestBody Chapter chapter, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            chapterServiceImpl.update(id, chapter);
            return ResponseEntity.ok(chapter);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Long id) {
        Chapter chapter = new Chapter();
        chapter.setId(id);
        try {
            chapterServiceImpl.delete(chapter);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/assignSurvey")
    public ResponseEntity<?> assignChaptersToSurvey(
        @RequestParam Long surveyId, 
        @RequestBody List<Long> chapterIds) {
        
        try {
            Surveys updatedSurvey = chapterServiceImpl.assingChaptersToSurvey(surveyId, chapterIds);
            return ResponseEntity.ok(updatedSurvey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
