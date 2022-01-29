package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.InfrastructureCreateDTO;
import com.barisertakus.toyotamanport.dto.PlantWithTrackDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;

import java.util.List;

public interface ApplicationPlantService{
    List<ApplicationPlant> saveByApplication(Application application, List<PlantWithTrackDTO> plantDTOList, List<InfrastructureCreateDTO> infrastructureDTOList);
    ApplicationPlant findByApplicationIdAndPlantId(Long applicationId, Long plantId);
    ApplicationPlant findById(Long applicationPlantId);
    ApplicationPlant getApplicationPlantByCountry(String country, List<ApplicationPlant> applicationPlants);
}
