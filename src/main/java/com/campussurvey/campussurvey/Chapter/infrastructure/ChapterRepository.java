package com.campussurvey.campussurvey.Chapter.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

}
