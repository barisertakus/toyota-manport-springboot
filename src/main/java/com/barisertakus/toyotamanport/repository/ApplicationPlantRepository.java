package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationPlantRepository extends JpaRepository<ApplicationPlant, Long> {
    Optional<ApplicationPlant> findByApplicationIdAndPlantId(Long applicationId, Long plantId);
}
