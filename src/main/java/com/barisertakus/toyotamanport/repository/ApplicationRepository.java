package com.barisertakus.toyotamanport.repository;

import com.barisertakus.toyotamanport.dto.ApplicationManagementDTO;
import com.barisertakus.toyotamanport.entity.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = "SELECT new com.barisertakus.toyotamanport.dto.ApplicationManagementDTO(" +
            "a.id, a.shortName, a.track, a.lineStopRisk," +
            "COUNT(a.id), a.businessAreaType )" +
            "FROM Application a " +
            "LEFT JOIN a.applicationPlants ap " +
            "WHERE a.isActive = TRUE " +
            "GROUP BY a.id",
            countQuery = "SELECT count(a.id) from Application a " +
                    "WHERE a.isActive = TRUE")
    Page<ApplicationManagementDTO> findApplicationsWithLivePlants(Pageable pageable);

    List<Application> findByIsActiveTrue();

    Application findByShortName(String shortName);
}
