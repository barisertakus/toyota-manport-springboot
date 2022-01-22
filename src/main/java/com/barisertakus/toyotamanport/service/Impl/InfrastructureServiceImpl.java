package com.barisertakus.toyotamanport.service.Impl;

import com.barisertakus.toyotamanport.dto.InfrastructureDTO;
import com.barisertakus.toyotamanport.entity.ApplicationPlant;
import com.barisertakus.toyotamanport.entity.Infrastructure;
import com.barisertakus.toyotamanport.repository.InfrastructureRepository;
import com.barisertakus.toyotamanport.service.ApplicationPlantService;
import com.barisertakus.toyotamanport.service.InfrastructureService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class InfrastructureServiceImpl implements InfrastructureService {
    private InfrastructureRepository infrastructureRepository;
    private ApplicationPlantService applicationPlantService;
    private ModelMapper modelMapper;

    public InfrastructureServiceImpl(InfrastructureRepository infrastructureRepository, ApplicationPlantService applicationPlantService, ModelMapper modelMapper) {
        this.infrastructureRepository = infrastructureRepository;
        this.applicationPlantService = applicationPlantService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean save(InfrastructureDTO infrastructureDTO, ApplicationPlant applicationPlant) {
        if(applicationPlant != null){
            Infrastructure infrastructure = modelMapper.map(infrastructureDTO, Infrastructure.class);
            infrastructureRepository.save(infrastructure);
            return true;
        }
        log.error("Application Plant record couldn't be found.");
        throw new IllegalArgumentException("An error occurred while saving the Infrastructure!");
    }

}
