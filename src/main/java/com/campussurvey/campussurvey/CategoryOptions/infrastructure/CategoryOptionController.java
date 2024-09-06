package com.campussurvey.campussurvey.CategoryOptions.infrastructure;

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

import com.campussurvey.campussurvey.CategoryOptions.application.CategoryOptionServiceImp;
import com.campussurvey.campussurvey.CategoryOptions.domain.entities.CategoryOption;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category-options")
public class CategoryOptionController {
    @Autowired
    private CategoryOptionServiceImp categoryOptionServiceImpl;

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping
    public List<CategoryOption> listAllCategoryOptions() {
        return categoryOptionServiceImpl.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createCategoryOption(@Valid @RequestBody CategoryOption categoryOption, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        categoryOptionServiceImpl.save(categoryOption);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryOption);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryOption(@PathVariable Long id, @Valid @RequestBody CategoryOption categoryOption, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            categoryOptionServiceImpl.update(id, categoryOption);
            return ResponseEntity.ok(categoryOption);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryOption(@PathVariable Long id) {
        CategoryOption categoryOption = new CategoryOption();
        categoryOption.setId(id);
        try {
            categoryOptionServiceImpl.delete(categoryOption);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
