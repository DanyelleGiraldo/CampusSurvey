package com.campussurvey.campussurvey.Option.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.campussurvey.campussurvey.Option.application.OptionServiceImpl;
import com.campussurvey.campussurvey.Option.domain.entities.Option;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/options")
public class OptionController {
    @Autowired
    private OptionServiceImpl optionServiceImpl;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping
    public List<Option> listAllOptions() {
        return optionServiceImpl.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createOption(@Valid @RequestBody Option option, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        optionServiceImpl.save(option);
        return ResponseEntity.status(HttpStatus.CREATED).body(option);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOption(@PathVariable Long id, @Valid @RequestBody Option option, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            optionServiceImpl.update(id, option);
            return ResponseEntity.ok(option);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable Long id) {
        Option option = new Option();
        option.setId(id);
        try {
            optionServiceImpl.delete(option);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
