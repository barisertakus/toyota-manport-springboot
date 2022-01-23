package com.barisertakus.toyotamanport.service;

import com.barisertakus.toyotamanport.dto.InfrastructureDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;

import java.util.List;

public interface InfrastructureService {
    Boolean save(InfrastructureDTO infrastructureDTO, ApplicationPlant applicationPlant);
    Boolean saveAll(List<InfrastructureDTO> infrastructureDTOList, ApplicationPlant applicationPlant);
}
