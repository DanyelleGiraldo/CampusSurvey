package com.campussurvey.campussurvey.Option.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Option.domain.entities.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
