package com.campussurvey.campussurvey.CategoryOptions.application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.CategoryOptions.domain.entities.CategoryOption;
import com.campussurvey.campussurvey.CategoryOptions.domain.service.CategoryOptionInterface;
import com.campussurvey.campussurvey.CategoryOptions.infrastructure.CategoryOptionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CategoryOptionServiceImp implements CategoryOptionInterface {
    @Autowired
    private CategoryOptionRepository categoryOptionRepository;

    @Override
    @Transactional
    public void save(CategoryOption categoryOption) {
        categoryOptionRepository.save(categoryOption);
    }

    @Override
    @Transactional
    public void delete(CategoryOption categoryOption) {
        categoryOptionRepository.delete(categoryOption);
    }

    @Override
    @Transactional
    public void update(Long id, CategoryOption updatedCategoryOption) {
        Optional<CategoryOption> existingCategoryOptionOpt = categoryOptionRepository.findById(id);

        if (existingCategoryOptionOpt.isPresent()) {
            CategoryOption existingCategoryOption = existingCategoryOptionOpt.get();

            existingCategoryOption.setOption(updatedCategoryOption.getOption());
            existingCategoryOption.setCategoriesCatalog(updatedCategoryOption.getCategoriesCatalog());
            existingCategoryOption.setUpdatedAt(updatedCategoryOption.getUpdatedAt());

            categoryOptionRepository.save(existingCategoryOption);
        } else {
            throw new EntityNotFoundException("CategoryOption not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public List<CategoryOption> findAll() {
        return categoryOptionRepository.findAll();
    }
}
