package com.campussurvey.campussurvey.Role.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Role.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(String name); 

}
