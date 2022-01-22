package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.entity.ApplicationPlant;

public interface ApplicationPlantService{
    ApplicationPlant findByApplicationIdAndPlantId(Long applicationId, Long plantId);
}
