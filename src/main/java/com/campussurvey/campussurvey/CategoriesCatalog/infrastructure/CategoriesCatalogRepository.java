package com.campussurvey.campussurvey.CategoriesCatalog.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.CategoriesCatalog.domain.entities.CategoriesCatalog;

public interface CategoriesCatalogRepository extends JpaRepository<CategoriesCatalog, Long> {
    @SuppressWarnings("null")
    @Override
    List<CategoriesCatalog> findAllById(Iterable<Long> ids);
}
