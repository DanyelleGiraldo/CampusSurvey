package com.campussurvey.campussurvey.CategoryOptions.domain.service;

import java.util.List;

import com.campussurvey.campussurvey.CategoryOptions.domain.entities.CategoryOption;

public interface  CategoryOptionInterface {
    void save(CategoryOption categoryOption);
    void delete(CategoryOption categoryOption);
    void update(Long id, CategoryOption categoryOption);
    List<CategoryOption>  findAll();
}
