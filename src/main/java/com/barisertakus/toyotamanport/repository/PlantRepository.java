package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    boolean existsByShortCodeOrFullName(String shortCode, String fullName);
}
