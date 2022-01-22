package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.InfrastructureDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Infrastructure;

public interface InfrastructureService {
    Boolean save(InfrastructureDTO infrastructureDTO, ApplicationPlant applicationPlant);
}
