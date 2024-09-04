package com.campussurvey.campussurvey.Option.domain.service;

import java.util.List;

import com.campussurvey.campussurvey.Option.domain.entities.Option;

public interface OptionInterface {
    void save(Option option);
    void delete(Option option);
    void update(Long id, Option option);
    List<Option>  findAll();
}   
