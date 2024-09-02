package com.campussurvey.campussurvey.User.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.User.domain.entities.User;

public interface  UserRepository extends  JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
