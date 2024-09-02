package com.campussurvey.campussurvey.Role.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campussurvey.campussurvey.Role.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Role findByName(String name); 

}
