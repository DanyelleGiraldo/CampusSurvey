package com.campussurvey.campussurvey.Chapter.domain.service;

import java.util.List;

import com.campussurvey.campussurvey.Chapter.domain.entities.Chapter;
import com.campussurvey.campussurvey.Survey.domain.entities.Surveys;

public interface ChapterInterface {
    void save(Chapter chapter);
    void delete(Chapter chapter);
    void update(Long id, Chapter chapter);
    List<Chapter>  findAll();
    Surveys assingChaptersToSurvey(Long surveId, List<Long> chapterIds);

}
