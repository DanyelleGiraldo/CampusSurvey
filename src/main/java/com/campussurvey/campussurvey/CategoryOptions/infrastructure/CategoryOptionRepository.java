package com.campussurvey.campussurvey.CategoryOptions.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.CategoryOptions.domain.entities.CategoryOption;

public interface CategoryOptionRepository extends JpaRepository<CategoryOption, Long>{
    List<CategoryOption> findAllById(Iterable<Long> ids);
}
