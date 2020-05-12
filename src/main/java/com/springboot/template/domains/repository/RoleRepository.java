package com.springboot.template.domains.repository;


import com.springboot.template.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("role")
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findById(String id);
}