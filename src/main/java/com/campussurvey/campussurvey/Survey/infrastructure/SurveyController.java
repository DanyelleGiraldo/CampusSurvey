package com.campussurvey.campussurvey.Survey.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.campussurvey.campussurvey.Survey.application.SurveyServiceImpl;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    @Autowired
    private SurveyServiceImpl surveyServiceImpl;

    @Autowired
    SurveyRepository surveyRepository;

    @GetMapping
    public List<Surveys> listAllSurveys() {
        return surveyServiceImpl.findAll();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping
    public ResponseEntity<?> createSurvey(@Valid @RequestBody Surveys surveys, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        surveyServiceImpl.save(surveys);
        return ResponseEntity.status(HttpStatus.CREATED).body("Survey created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSurvey(@PathVariable Long id, @Valid @RequestBody Surveys updatedSurveys, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            Optional<Surveys> existingSurveyOptional = surveyRepository.findById(id);
            if (existingSurveyOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + id);
            }

            surveyServiceImpl.update(id, updatedSurveys);
            return ResponseEntity.ok("Survey updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating survey: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurvey(@PathVariable Long id) {
        try {
            Optional<Surveys> survey = surveyServiceImpl.findAll().stream().filter(s -> s.getId().equals(id)).findFirst();
            if (survey.isPresent()) {
                surveyServiceImpl.delete(survey.get());
                return ResponseEntity.ok("Survey deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting survey: " + e.getMessage());
        }
    }

    @PostMapping("/{surveyId}/categories")
    public Surveys assignCategoriesToSurvey(@PathVariable Long surveyId, @RequestBody List<Long> categoryIds) {
        return surveyServiceImpl.assignCategoriesToSurvey(surveyId, categoryIds);
    }
}