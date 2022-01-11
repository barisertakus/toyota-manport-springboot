package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    long count();
}
