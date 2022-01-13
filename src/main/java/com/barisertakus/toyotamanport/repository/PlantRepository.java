package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    boolean existsByShortCodeOrFullName(String shortCode, String fullName);
    Page<Plant> findByIsActiveTrue(Pageable pageable);
}
