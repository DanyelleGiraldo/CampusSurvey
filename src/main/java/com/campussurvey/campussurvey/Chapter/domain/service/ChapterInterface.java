package com.campussurvey.campussurvey.Chapter.domain.service;

import java.util.List;

import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;

public interface ChapterInterface {
    void save(Chapter chapter);
    void delete(Chapter chapter);
    void update(Long id, Chapter chapter);
    List<Chapter>  findAll();
}
