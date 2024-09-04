package com.campussurvey.campussurvey.Option.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campussurvey.campussurvey.Option.domain.entities.Option;
import com.campussurvey.campussurvey.Option.domain.service.OptionInterface;
import com.campussurvey.campussurvey.Option.infrastructure.OptionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OptionServiceImpl implements OptionInterface {

    @Autowired
    OptionRepository optionRepository;

    @Override
    public void save(Option option) {
        optionRepository.save(option);
    }

    @Override
    public void delete(Option option) {
        optionRepository.delete(option);
    }

    @Override
    public void update(Long id, Option option) {
        Optional<Option> existingOption= optionRepository.findById(id);

        if(existingOption.isPresent()){
            Option optionfound = existingOption.get();

            optionfound.setOptionText(option.getOptionText());
            optionfound.setUpdatedAt(LocalDateTime.now());

            optionRepository.save(optionfound);
        } else {
            throw new EntityNotFoundException("Option not found with id: " + id);        
        }
    }

    @Override
    public List<Option> findAll() {
        return optionRepository.findAll();
    }

}
