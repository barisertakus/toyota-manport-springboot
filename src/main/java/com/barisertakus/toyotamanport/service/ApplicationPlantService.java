package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.PlantWithTrackDTO;
import com.barisertakus.toyotamanport.entity.Application;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;

import java.util.List;

public interface ApplicationPlantService{
    List<ApplicationPlant> saveByApplicationAndPlantList(Application application, List<PlantWithTrackDTO> plantDTOList);
    ApplicationPlant findByApplicationIdAndPlantId(Long applicationId, Long plantId);
    ApplicationPlant findById(Long applicationPlantId);
}
