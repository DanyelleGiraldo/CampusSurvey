package com.campussurvey.campussurvey.Question.infrastructure;

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
import org.springframework.web.bind.annotation.RestController;

import com.campussurvey.campussurvey.Question.application.QuestionServiceImp;
import com.campussurvey.campussurvey.Question.domain.entities.Question;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionServiceImp questionServiceImp;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public List<Question> listAllQuestions(){
        return questionServiceImp.findAll();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@Valid @RequestBody Question question, BindingResult result){
        if(result.hasErrors()){
            return validation(result);
        }
        questionServiceImp.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body("Question created succesfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long id, @Valid @RequestBody Question updatedQuestion, BindingResult result){
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            Optional<Question> existingQuestionOptional = questionRepository.findById(id);
            if(existingQuestionOptional.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Survey not found with id: " + id);
            }

            questionServiceImp.update(id, updatedQuestion);
            return ResponseEntity.ok("question updated successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating question: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        try {
            Optional<Question> question = questionServiceImp.findAll().stream().filter(s -> s.getId().equals(id)).findFirst();
            if (question.isPresent()) {
                questionServiceImp.delete(question.get());
                return ResponseEntity.ok("Question deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found with id: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting question: " + e.getMessage());
        }
    }
}